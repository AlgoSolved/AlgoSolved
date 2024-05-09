#VPC Variables
variable "vpc_id" {
  type = string
}
variable "sub_pub_ex1_id" {
  type = string
}
variable "sub_pub_ex2_id" {
  type = string
}

#EC2 Variables
variable "ec2_instance_type" {
  type = string
}

#Common Variables
variable "service" {
  description = "The name of the service"
  type        = string
  default     = "algosolved"
}
