# Les EJB 3 (avec Struts 2, JSF 2, JasperReports 3, Flex 3)

This project hosts the code for the book **Les EJB 3 (avec Struts 2, JSF 2, JasperReports 3, Flex 3)**.

[Buy the book](http://www.editions-eni.fr/Livres/Les-EJB-3-avec-Struts-2--JSF-2--JasperReports-3--Flex-3-Developpez-pour-le-web-par-l-exemple--3-applications-detaillees/.6_3a6222cf-b921-41f5-886c-c989f77ba994_75c514cc-b7b3-49ed-a7ca-c8e516ab7429_e5bf3eda-7ad1-4a29-a943-f93cf015b594_1_0_d9bd8b5e-f324-473f-b1fc-b41b421c950f.html)

Introduction
============

A shopping cart web application for selling products over the internet, declined in 3 versions for the front-end layer (Struts 2, JSF 2 and Flex 3). The business and persistence layers are based on EJB 3. 

I use the EJB container bundled with JBoss 6 application server to :
- write the persistence layer using Entity beans, the JPQL language and the Java Persistence API
- create business classes based on Session and Message-Driven beans
- create a security policy with roles and permissions that are defined in properties file, a database or a LDAP
- expose EJB 3 as web services
- create asynchronous tasks with the help of EJB Timers
- provide a little of aspect-oriented programming with interceptors 

I also use :
- the GraniteDS framework to establish the communication between Java objects and Flex 3, to create rich interfaces (RIA).
- the JasperReports framework to generate reports.

Finally, the CarnetContactsEJB31 project demonstrates some of the new features brought by EJB 3.1

Installation
============

The projects can be imported into Eclipse. You need to add the missing jars and configure the projects build paths.
Compilation is done with Eclipse.
I plan to mavenize all the projects in the future.

Do you have a question ?
========================

Got a question or a suggestion ? Or maybe you just want to critic my code ? Do not hesitate, drop me an email at :

cel975@yahoo.com

Celinio Fernandes

[Visit my blog](http://www.celinio.net/techblog)