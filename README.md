<html>
<head>
  
</head>
<body>

<p align="center">
  <img width="1400" height="500" src="src/main/resources/img/Maze.jpg">
</p>

<h1>DEDALO</h1>
<div align="justify">
  
DEDALO provides a framework to design an  <a href="https://maths.dur.ac.uk/lms/108/talks/1282zane.pdf"> informed proposal distribution </a> in the context of   <a href="https://en.wikipedia.org/wiki/Metropolis%E2%80%93Hastings_algorithm"> Metropolis-Hastings</a> algorithms employed to find the agent-based stochastic user equilibrium in  <a href="https://matsim.org/">MATSim</a>  . The proposal distribution is used to select new travel choices (e.g., transport mode between two activities, departure time, routes) in an activity-based model, which represents the daily plan of every agent in the synthetic population. In order to obtain a better convergence rate and reduce the <a href="https://arxiv.org/pdf/1909.11827.pdf"> auto-correlation between samples </a>, the proposal distribution needs  to  approximate  the  target  distribution  which  is  considered,  in  this  work,  a <a href="https://eml.berkeley.edu/choice2/ch6.pdf"> mixture-logit model</a>.  However, to be computationally efficient, this requires to solve two main obstacles.  The first one stems from the combinatorial nature of all the possible choices a traveler deals with during a daily plan and the second one is due to the unknown stochastic disturbance the agent faces during the execution of his/her plan as a result of the stochastic decisions of all the other agents. DEDALO overcomes these obstacles, providing the agents  with  an  augmented  capacity  to  forecast  the  choices  of  the  other  agents by  knowing  the probability of their decisions and adapting accordingly.

</div>

<h1> Requirements</h1>

<h3>Runtime</h3>
<ul>
  <li>JRE 11: Java runtime version 11 is needed for using the library.</li>
</ul>

<h3>Build time</h3>
<ul>
  <li>JDK 11: The Java JDK 11 must be installed.</li>
</ul>

<h1> Installing</h1>
Please note that this library is still in early development stages. Major API changes are expected in the near future.

<h1>Versioning and Packaging</h1>

<h1>Contact</h1>
<div align="justify">
E-Mail Adresse: stefano.penazzi@ivt.baug.ethz.ch <br />
<a href="https://www.linkedin.com/in/stefano-penazzi-datascientist/">LinkedIn</a> <br />
</div>

<h1>References</h1>
<a id="1">[1]</a> 
<a href="https://matsim.org/the-book">The Multi-Agent Transport Simulation MATSim</a><br />

</body>
</html>
