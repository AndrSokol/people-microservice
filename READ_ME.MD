- Build docker image and push it
docker build -t 13011990/area:3.0 .
docker push 13011990/area:3.0

- Create kubernetes files
- Run one by one:
kubectl apply -f {secrets/ config-map/ postgre-deployment/ area-deployment}
- check that works well:
kubectl get all
- Create an SSH tunnel to k8s:
kubectl port-forward svc/area 8089:8089


Integration with Jenkins:
https://medium.com/@mudasirhaji/how-to-deploy-your-code-on-a-docker-container-using-jenkins-on-aws-2a8eca3159aa
AWS:
1. Create EC2 instance
2. Create .pem file if needed and set permissions: chmod 400 jenkins_kp.pem
3. Connect to instance (Make sure that instance has SG with inbound rule "SSH Anywhere")
   ssh -i "jenkins_kp.pem" ec2-user@ec2-54-224-201-67.compute-1.amazonaws.com
4. Install Jenkins (Make sure you have outbound rules in SG for HTTP/HTTPS Anywhere)
5. Install Java: sudo yum install java-11-amazon-corretto-devel
6. Install Jenkins: sudo yum install jenkins
7. Login to Jenkins:
   4c487ae027014279b60808b3d40cbf48
  This may also be found at: /home/ec2-user/.jenkins/secrets/initialAdminPassword
  Set admin User: admin / qwert12345
  URL: http://ec2-54-224-201-67.compute-1.amazonaws.com:8080/

8. Install git: sudo yum install git And other tools
9. Run docker host instance 
 ssh -i "jenkins_kp.pem" ec2-user@ec2-52-72-1-158.compute-1.amazonaws.com
Install and run docker: sudo yum install docker -y 
Start Docker:
- sudo systemctl enable docker
- sudo systemctl start docker
- make magic with tomcat docker image
- run tomcat image: docker run -d --name tomcat-server -p 8085:8080 tomcatserver
- open in browser: http://52.72.1.158:8085/ (Make sure you have outbound rule for 8081-9000 ports)
- Create dockeradmin (dockeradmin / qwert12345) and login to ssh -i "jenkins_kp.pem" dockeradmin@ec2-52-72-1-158.compute-1.amazonaws.com