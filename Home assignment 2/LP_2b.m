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
%options = optimoptions('linprog', 'Algorithm', 'interior-point', 'Display', 'iter');
%options = optimoptions('linprog', 'Algorithm', 'interior-point', 'Display', 'off');
%options = optimoptions('linprog', 'Algorithm', 'dual-simplex', 'Display', 'iter');
options = optimoptions('linprog', 'Algorithm', 'dual-simplex', 'Display', 'off');
[x,fval,exitflag,output,lambda] = linprog(c', A, b, [], [], lb, [], [], options)