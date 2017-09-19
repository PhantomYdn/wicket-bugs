# Example of Double Slash bug in wicket

Steps to reproduce

1) Clone repository localy
2) Run as `mvn jetty:run`
3) Go to http://localhost:8080
4) Second link should lead to NotFoundPage, but when you click: all styles are broken. But if you go directly to it as http://localhost:8080/404 - everything is fine 
