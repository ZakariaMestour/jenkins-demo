package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController

public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@GetMapping("/docker")
	public String client() {
		return "<!DOCTYPE html>" +
				"<html>" +
				"<head>" +
				"<title>Projet DevOps</title>" +
				"<style>" +
				"@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@600;700&display=swap');" +
				"* { margin: 0; padding: 0; box-sizing: border-box; }" +
				"body { " +
				"  font-family: 'Poppins', Arial, sans-serif; " +
				"  text-align: center; " +
				"  min-height: 100vh; " +
				"  display: flex; " +
				"  align-items: center; " +
				"  justify-content: center; " +
				"  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); " +
				"  overflow: hidden;" +
				"}" +
				".card { " +
				"  background: rgba(255, 255, 255, 0.95); " +
				"  padding: 60px 80px; " +
				"  border-radius: 20px; " +
				"  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3); " +
				"  backdrop-filter: blur(10px); " +
				"  animation: fadeInUp 0.8s ease-out; " +
				"  position: relative;" +
				"}" +
				"@keyframes fadeInUp { " +
				"  from { opacity: 0; transform: translateY(30px); } " +
				"  to { opacity: 1; transform: translateY(0); } " +
				"}" +
				"@keyframes gradient { " +
				"  0% { background-position: 0% 50%; } " +
				"  50% { background-position: 100% 50%; } " +
				"  100% { background-position: 0% 50%; } " +
				"}" +
				"@keyframes sparkle { " +
				"  0%, 100% { opacity: 0; transform: scale(0); } " +
				"  50% { opacity: 1; transform: scale(1); } " +
				"}" +
				"h1 { " +
				"  background: linear-gradient(45deg, #667eea, #764ba2, #f093fb, #667eea); " +
				"  background-size: 300% 300%; " +
				"  -webkit-background-clip: text; " +
				"  -webkit-text-fill-color: transparent; " +
				"  background-clip: text; " +
				"  font-size: 56px; " +
				"  font-weight: 700; " +
				"  margin-bottom: 30px; " +
				"  animation: gradient 3s ease infinite; " +
				"  text-shadow: 2px 2px 4px rgba(0,0,0,0.1); " +
				"  letter-spacing: 2px;" +
				"}" +
				"p { " +
				"  color: #555; " +
				"  font-size: 22px; " +
				"  margin: 15px 0; " +
				"  font-weight: 600;" +
				"}" +
				".author { " +
				"  font-size: 18px !important; " +
				"  margin-top: 40px !important; " +
				"  color: #888 !important; " +
				"  font-weight: 400 !important;" +
				"}" +
				".sparkle { " +
				"  position: absolute; " +
				"  width: 10px; " +
				"  height: 10px; " +
				"  background: radial-gradient(circle, #fff, #667eea); " +
				"  border-radius: 50%; " +
				"  animation: sparkle 2s infinite;" +
				"}" +
				".sparkle:nth-child(1) { top: 20px; left: 30px; animation-delay: 0s; }" +
				".sparkle:nth-child(2) { top: 50px; right: 40px; animation-delay: 0.5s; }" +
				".sparkle:nth-child(3) { bottom: 30px; left: 50px; animation-delay: 1s; }" +
				".sparkle:nth-child(4) { bottom: 60px; right: 30px; animation-delay: 1.5s; }" +
				".emoji { " +
				"  font-size: 64px; " +
				"  margin-bottom: 20px; " +
				"  display: inline-block; " +
				"  animation: float 3s ease-in-out infinite;" +
				"}" +
				"@keyframes float { " +
				"  0%, 100% { transform: translateY(0px); } " +
				"  50% { transform: translateY(-20px); } " +
				"}" +
				"</style>" +
				"</head>" +
				"<body>" +
				"<div class='card'>" +
				"<div class='sparkle'></div>" +
				"<div class='sparkle'></div>" +
				"<div class='sparkle'></div>" +
				"<div class='sparkle'></div>" +
				"<div class='emoji'>🚀</div>" +
				"<h1>Merci pour votre attention !</h1>" +
				"<p>Projet CI/CD avec Jenkins & Kubernetes</p>" +
				"<p class='author'>Réalisé par Zakaria Mestour</p>" +
				"</div>" +
				"</body>" +
				"</html>";
	}
}
