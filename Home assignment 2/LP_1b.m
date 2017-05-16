clc
clear all

c = [-4;
     -3;
     -2;
     -2;
     -1];
 
A = [2 0 0 0 0;
     0 2 2 2 1;
     0.2 1 0 0.5 0;
     1 0 0 0 0;
     0 0 1 0 0;
     1 1 1 0 0;
     0 0 0 1 1];
 
 b = [36;
      216;
      18;
      16;
      2;
      34;
      28];
  
 lb = [0;
       0;
       0;
       0;
       0];
   
 %options = optimoptions('linprog', 'Algorithm', 'interior-point', 'Display', 'iter');
 %options = optimoptions('linprog', 'Algorithm', 'interior-point', 'Display', 'off');
 %options = optimoptions('linprog', 'Algorithm', 'dual-simplex', 'Display', 'iter');
 options = optimoptions('linprog', 'Algorithm', 'dual-simplex', 'Display', 'off');
 [x,fval,exitflag,output,lambda] = linprog(c, A, b, [], [], lb, [], [], options)