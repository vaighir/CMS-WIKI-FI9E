import { Component, OnInit } from '@angular/core';
import { ArticleModelModule } from '../../article-model/article-model.module';
import { ArticleServiceService } from '../../services/article-service.service';

@Component({
  selector: 'app-articles-add',
  templateUrl: './articles-add.component.html',
  styleUrls: ['./articles-add.component.scss']
})
export class ArticlesAddComponent implements OnInit {
  article: ArticleModel = new ArticleModel();
  private articleService = this.injector.get(ArticleServiceService);

  constructor(private injector: Injector) {
    
  }

  ngOnInit(): void {

  }

  onSave(): void {
    this.articleService.store(this.article);
  }
}