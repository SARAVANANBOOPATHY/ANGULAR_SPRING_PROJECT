import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class FileDownloadService {
  private fileDownloadEndpoint = 'http://localhost:8080/download'; // Replace with your backend URL
  private downloadFileEndpoint = `${this.fileDownloadEndpoint}/download`;

  constructor(private http: HttpClient) { }

  downloadFile(filename: string) {
    const options = { responseType: 'blob' as 'json' }; // Use blob for file downloads
    return this.http.get(`${this.downloadFileEndpoint}/${filename}`, options);
  }
}
