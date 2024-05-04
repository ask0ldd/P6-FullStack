import { ILimitedUser } from "./IUser"

export interface ITopic {
  id: number
  name: string
  description: string
  createdAt: string
  updatedAt: string
  users: Array<ILimitedUser>
}

export interface ILimitedTopic{
  id: number
  name: string
  description: string
}