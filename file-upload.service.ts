import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class FileUploadService {
  constructor(private http: HttpClient) { }

  uploadFile(file: FormData) {
    return this.http.post(uploadFileEndpoint, file);
  }
}

export const fileUploadEndpoint = 'http://localhost:8080/upload'; // Replace with your backend URL
export const uploadFileEndpoint = `${fileUploadEndpoint}/upload`;
