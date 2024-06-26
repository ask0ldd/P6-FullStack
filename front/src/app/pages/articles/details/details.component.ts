import { Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, Subscription, of, take } from 'rxjs';
import { IArticle } from 'src/app/interfaces/IArticle';
import { ILimitedComment } from 'src/app/interfaces/IComment';
import { ArticleService } from 'src/app/services/article.service';
import { CommentService } from 'src/app/services/comment.service';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.scss']
})
export class DetailsComponent implements OnInit, OnDestroy {

  @ViewChild('commentTextarea')
  commentTextarea!: ElementRef;
  
  article$ : Observable<IArticle> = of()
  urlId! : string | null
  comments! : Array<ILimitedComment>
  articleSub! : Subscription
  newCommentSub! : Subscription

  constructor(private articleService : ArticleService, private commentService : CommentService, private route: ActivatedRoute) { 
  }
  
  ngOnInit(): void {
    this.urlId = this.route.snapshot.paramMap.get('id')
    if(this.urlId != null ) {
      this.article$ = this.articleService.detail$(this.urlId)
      this.articleSub = this.article$.pipe(take(1)).subscribe(article => this.comments = article.comments)
    }
  }

  onSendNewComment(){
    const commentText = this.commentTextarea.nativeElement.value
    const articleId = this.route.snapshot.paramMap.get('id')
    if(articleId == null || commentText == null) return
    this.newCommentSub = this.commentService.create$({articleId : articleId, comment : commentText}).subscribe(_ => this.refreshComments())
    this.commentTextarea.nativeElement.value = ""
  }

  refreshComments(): void{
    this.articleSub.unsubscribe()
    this.articleSub = this.article$.subscribe(article => this.comments = article.comments)
    this.newCommentSub.unsubscribe() // can unsub since comments have been refreshed
  }

  ngOnDestroy(): void {
    if(this.articleSub) this.articleSub?.unsubscribe()
    if(this.newCommentSub) this.newCommentSub?.unsubscribe()
  }
}
