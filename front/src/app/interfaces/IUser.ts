export interface IUser {
  id: number
  email: string
  username: string
  password: string
  createdAt: string
  updatedAt: string
}

export interface ILimitedUser{
  id : number
  username : string
}