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
  type      = string
  sensitive = true
}
variable "sub_pub_a_id" {
  type      = string
  sensitive = true
}
variable "sub_pub_b_id" {
  type      = string
  sensitive = true
}
variable "sub_pub_c_id" {
  type      = string
  sensitive = true
}
variable "sub_pub_d_id" {
  type      = string
  sensitive = true
}
variable "sub_pri_a_id" {
  type      = string
  sensitive = true
}
variable "sub_pri_b_id" {
  type      = string
  sensitive = true
}
variable "sub_pri_c_id" {
  type      = string
  sensitive = true
}
variable "sub_pri_d_id" {
  type      = string
  sensitive = true
}


## ec2
variable "ec2_instance_type" {
  type = string
}
variable "ec2_key" {
  type      = string
  sensitive = true
}

# lambda
variable "lambda_function_name" {
  type = string
}
