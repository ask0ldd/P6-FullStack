import { IUser } from "./IUser"

export interface ITopic {
    id: number
    name: string
    description: string
    createdAt: string
    updatedAt: string
    users: Array<IUser>
  }