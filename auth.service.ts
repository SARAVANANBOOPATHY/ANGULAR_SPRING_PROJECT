import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private authEndpoint = 'http://localhost:8080/auth'; 
  private loginEndpoint = `${this.authEndpoint}/login`;

  constructor(private http: HttpClient) { }

  login(credentials: { username: string, password: string }) {
    return this.http.post(this.loginEndpoint, credentials);
  }
}
