import { IArticle } from "./IArticle"
import { IUser } from "./IUser"

export interface IComment {
    id: number
    content: string
    article: IArticle
    user: IUser
    createdAt: Date
    updatedAt: Date
  }