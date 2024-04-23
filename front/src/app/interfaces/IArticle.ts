import { IComment } from "./IComment";
import { ITopic } from "./ITopic";
import { IUser } from "./IUser";

export interface IArticle {
    id: number
    title: string
    content: string
    comments: IComment[]
    topic: ITopic
    user: IUser
    createdAt: Date
    updatedAt: Date
  }