package com.semanticSquare.thrillio.bgjobs;

import com.semanticSquare.thrillio.dao.BookmarkDao;
import com.semanticSquare.thrillio.entities.WebLink;
import com.semanticSquare.thrillio.util.HttpConnect;
import com.semanticSquare.thrillio.util.IOUtil;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class WebpageDownloaderTask implements Runnable {
    private static BookmarkDao dao = new BookmarkDao();
    private static final long TIME_FRAME = 3000000000L;
    private boolean downloadAll = false;
    ExecutorService downloadExecutor = Executors.newFixedThreadPool(5);
    private static class Downloader<T extends WebLink> implements Callable<T> {
        private T weblink;
        public Downloader(T weblink) {
            this.weblink = weblink;
        }
        public T call() {
            try {
                if (!weblink.getUrl().endsWith(".pdf")) {
                    weblink.setDownloadStatus(WebLink.DownloadStatus.FAILED);
                    String htmlPage = HttpConnect.download(weblink.getUrl());
                    weblink.setHtmlPage(htmlPage);
                } else {
                    weblink.setDownloadStatus(WebLink.DownloadStatus.NOT_ELIGIBLE);

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return weblink;
        }
    }
    public WebpageDownloaderTask(boolean downloadAll) {
        this.downloadAll = downloadAll;
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            //get weblinks
            List<WebLink> webLinks = getWeblinks();

            //download concurently
            if (webLinks.size() > 0) {
                download(webLinks);
            }
            else {
                System.out.println("No new weblinks to download");
            }

            //wait
            try {
                TimeUnit.SECONDS.sleep(15);
            } catch (InterruptedException e) {
            }
        }
        downloadExecutor.shutdown();
    }

    private void download(List<WebLink> webLinks) {
        List<Downloader<WebLink>> tasks = getTasks(webLinks);
        List<Future<WebLink>> futures = new ArrayList<>();

        try {
            futures = downloadExecutor.invokeAll(tasks, TIME_FRAME, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Future<WebLink> future : futures) {
            try {
                if (!future.isCancelled()) {
                    WebLink weblink = future.get();
                    String webPage = weblink.getHtmlPage();
                    if(webPage != null) {
                        IOUtil.write(webPage, weblink.getId());
                        weblink.setDownloadStatus(WebLink.DownloadStatus.SUCCESS);
                        System.out.println("Download Success: "+ weblink.getUrl());
                    } else {
                        System.out.println("Webpage not downloaded: "+ weblink.getUrl());
                    }
                } else {
                    System.out.println("\n\nTask is cancelled --> " + Thread.currentThread());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Downloader<WebLink>> getTasks(List<WebLink> webLinks) {
        List<Downloader<WebLink>> tasks = new ArrayList<>();

        for( WebLink webLink : webLinks) {
            tasks.add(new Downloader<WebLink>(webLink));
        }
        return tasks;
    }

    private List<WebLink> getWeblinks() {
        List<WebLink> webLinks = null;
        if (downloadAll) {
            webLinks = dao.getAllWebLinks();
            downloadAll = false;
        } else {
            webLinks = dao.getWebLinks(WebLink.DownloadStatus.NOT_ATTEMPTED);
        }
        return webLinks;
    }
}
