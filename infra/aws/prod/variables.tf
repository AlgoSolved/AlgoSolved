## common
variable "project" {
  type = string
}
variable "service" {
  type = string
}
variable "stage" {
  type = string
}

## vpc
variable "vpc_id" {
  type = string
}
variable "sub_pub_a_id" {
  type = string
}
variable "sub_pub_b_id" {
  type = string
}
variable "sub_pub_c_id" {
  type = string
}
variable "sub_pub_d_id" {
  type = string
}

## ec2
variable "ec2_instance_type" {
  type = string
}
variable "ec2_key" {
  type = string
}

# lambda
variable "lambda_function_name" {
  type = string
}
