import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { MainComponent } from './main/main.component';
import { MainNavigationComponent } from './main-navigation/main-navigation.component';
import { ArticlesComponent } from './main/articles/components/articles/articles.component';
import { AppRoutingModule } from './app-routing.module';
import { ArticlesAddComponent } from './main/articles/components/articles-add/articles-add.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AddHeaderInterceptor } from './http-interceptor/http-interceptor';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { LoginComponent } from './main/login/components/login.component';
import { ArticleItemComponent } from './main/articles/components/articles/article-item/article-item.component';
import { ArticleDetailComponent } from './main/articles/components/article-detail/article-detail.component';
import { TagComponent } from './nav-menu/components/tag/tag.component';
import { NavMenuComponent } from './nav-menu/components/nav-menu.component';
import { LoaderComponent } from './main/loader/loader-component/loader-component.component';
import { ArticleEditComponent } from './main/articles/components/article-edit/article-edit.component';

@NgModule({
  declarations: [
    AppComponent,
    NavMenuComponent,
    MainComponent,
    MainNavigationComponent,
    ArticlesComponent,
    ArticlesAddComponent,
    LoginComponent,
    ArticleItemComponent,
    ArticleDetailComponent,
    TagComponent,
    LoaderComponent,
    ArticleEditComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: AddHeaderInterceptor,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
