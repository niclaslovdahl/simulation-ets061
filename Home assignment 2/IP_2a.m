clc
clear all

c = [-1;
     -5];
A = [2 -1;
     -1 1;
     1 4];
 b = [4;
      1;
      12];
 lb = [0;
       0];
 %options = optimoptions('intlinprog', 'Display', 'iter');
 options = optimoptions('intlinprog', 'Display', 'off');
 intcon = [1;
           2];
[x, fval, exitflag, output] = intlinprog(c', intcon, A, b, [], [], lb, [], options)