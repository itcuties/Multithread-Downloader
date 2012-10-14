package com.itcuties.samples;

public class DownloadThread extends Thread {
	private String imageUrl;
	private String destinationPath;
	private Lock lock;
	
	public DownloadThread(String imageUrl, String destinationPath, Lock lock) {
		this.imageUrl = imageUrl;
		this.destinationPath = destinationPath;
		this.lock = lock;
	}
	
	@Override
	public void run() {
		try {
			lock.addRunningThread();
			
			new ImageDownloader(imageUrl, destinationPath).download();
			
			lock.removeRunningThread();
			
			synchronized (lock) {
				lock.notify();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}