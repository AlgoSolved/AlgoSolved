output "latest_ami_id" {
  value = data.aws_ami.ubuntu.id
  description = "The ID of the latest Ubuntu AMI"
}
