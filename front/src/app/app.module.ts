import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { AuthModule } from './pages/auth/auth.module';
import { TopicsComponent } from './pages/topics/topics.component';
import { UserProfileComponent } from './pages/user-profile/user-profile.component';
import { SharedModule } from './shared/shared.module';
import { ArticlesModule } from './pages/articles/articles.module';
import { RouterOutlet } from '@angular/router';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { JwtInterceptor } from './interceptor/jwt.interceptor';

@NgModule({
  declarations: [AppComponent, HomeComponent, TopicsComponent, UserProfileComponent],
  imports: [
    BrowserModule,
    AuthModule,
    ArticlesModule,
    SharedModule,
    AppRoutingModule,
  ],
  // providers: [],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
