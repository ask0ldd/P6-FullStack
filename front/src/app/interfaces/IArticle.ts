import { IComment, ILimitedComment } from "./IComment";
import { ITopic } from "./ITopic";
import { ILimitedUser, IUser } from "./IUser";

export interface IArticle {
  id: number
  title: string
  content: string
  comments: ILimitedComment[]
  topic: ITopic
  user: ILimitedUser
  createdAt: Date
  updatedAt: Date
}