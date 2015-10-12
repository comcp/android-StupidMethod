package com.stupid.method.util.http;

public interface IXDownloadProgress {
	void onProgress(int bytesWritten, int totalSize);
}
