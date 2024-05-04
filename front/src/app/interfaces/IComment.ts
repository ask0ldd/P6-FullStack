import { IArticle } from "./IArticle"
import { ILimitedUser, IUser } from "./IUser"

export interface IComment {
  id: number
  content: string
  article: IArticle
  user: IUser
  createdAt: Date
  updatedAt: Date
}

export interface ILimitedComment{
    id : number
    user : ILimitedUser
    content : string
    createdAt: Date
    updatedAt: Date;
}