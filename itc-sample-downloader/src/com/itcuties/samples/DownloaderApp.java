package com.itcuties.samples;

public class DownloaderApp {

	public static void main(String[] args) {
		// Images URLs
		String[] imagesToDownload = new String[] {
				"http://fbapp.itcuties.com/middle/_DSC4598.jpg",
				"http://fbapp.itcuties.com/middle/_DSC4516.jpg",
				"http://fbapp.itcuties.com/middle/_DSC4796.jpg",
				"http://fbapp.itcuties.com/middle/_DSC4776.jpg",
				"http://fbapp.itcuties.com/middle/_DSC4505.jpg",
				"http://fbapp.itcuties.com/middle/_DSC4448.jpg",
				"http://fbapp.itcuties.com/middle/_DSC4590.jpg",
				"http://fbapp.itcuties.com/middle/_DSC4514.jpg",
				"http://fbapp.itcuties.com/middle/_DSC4698.jpg",
				"http://fbapp.itcuties.com/middle/_DSC4434.jpg"
		}; 
		
		String destinationFolder = "D:\\Workspace\\ITC\\tmp\\";
		
		try {
			int nameIndex 	= 0;
			long startTime 	= 0;
			long endTime 	= 0;
			
			// Download images in this thread
			System.out.println("Downloading "+imagesToDownload.length+" images with single thread");
			startTime = System.currentTimeMillis();
			for (String url: imagesToDownload) {
				new ImageDownloader(url,destinationFolder + "single" + (nameIndex++) + ".jpg").download();
			}
			endTime = System.currentTimeMillis();
			System.out.println("\tdownload time: " + (endTime-startTime)+" ms");
			
			// Download images in multiple threads
			nameIndex 	= 0;
			Lock lock = new Lock();	// A lock object to synchronize threads on it.
			System.out.println("Downloading "+imagesToDownload.length+" images with multiple threads");
			startTime = System.currentTimeMillis();
			for (String url: imagesToDownload) {
				DownloadThread dt = new DownloadThread(url, destinationFolder + "multiple" + (nameIndex++) + ".jpg", lock);
				dt.start();	// Start download in another thread
			}
			
			// Wait here for all the threads to end
			while (lock.getRunningThreadsNumber() > 0)
				synchronized (lock) {
					lock.wait();
				}
			
			endTime = System.currentTimeMillis();
			System.out.println("\tdownload time: " + (endTime-startTime)+" ms");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
