%   TSP_GA Traveling Salesman Problem (TSP) Genetic Algorithm (GA).
%   Finds a (near) optimal solution to the TSP by setting up a GA to search
%   for the shortest route (least distance for the salesman to travel to
%   each city exactly once and return to the starting city)
%
% Summary:
%     1. A single salesman travels to each of the cities and completes the
%        route by returning to the city he started from
%     2. Each city is visited by the salesman exactly once
%
% Input:
%     USERCONFIG (structure) with zero or more of the following fields:
%     - xy (float) is an Nx2 matrix of city locations, where N is the number of cities
%     - popSize (scalar integer) is the size of the population (should be divisible by 4)
%     - numGen (scalar integer) is the number of desired iterations for the algorithm to run
%     - mutProb (float) is the probability of mutation per individual
%     - crossProb (float) is the probability of cross-over
%     - eliteFract (float) is used for Elitism. It is the fraction of good members of the current
%     generation which should replace the weak members in the next generation.

% Input Notes:
%     1. Rather than passing in a structure containing these fields, any/all of
%        these inputs can be passed in as parameter/value pairs in any order instead.
%     2. Field/parameter names are case insensitive but must match exactly otherwise.
%
% Output:
%     RESULTSTRUCT (structure) with the following fields:
%         (in addition to a record of the algorithm configuration)
%     - OPTROUTE (integer array) is the best route found by the algorithm
%     - MINDIST (scalar float) is the cost of the best route

clc;
clear all;

% first, delete any old mat file from the workspace (you do not need to change this code)
if exist('cities.mat', 'file')==2
    delete('cities.mat');
end


%% Then, choose which data file you wish to experiment with.
% Below is the list of various data files with different number of cities:

% loadatt48();         % 48 cities
% loadst70();          % 70 cities
% loadgr96();          % 96 cities

% I have chosen a data file from the above list
loadgr96();

%% prepare the distance matrix
load('cities.mat');
xy = cities';

% you should update the following code to obtain the average and 95%
% confidence interval for each configuration of numGen
data = zeros(20,15);
i = 1;
for numGen = 100:100:2000
    for runs = 1:15
        userConfig = struct('xy', xy, 'popSize', 200, 'numGen', numGen, 'crossProb', 0.25, 'mutProb', 0.5, 'eliteFract', 0.02);
        resultStruct = tsp_ga(userConfig);
        data(i,runs) = resultStruct.minDist;
        % the best tour found by GA
        % fprintf('\nBest tour found by GA:\n');
        % resultStruct.optRoute
        
        % the distance of the best tour
        fprintf('\n Number of generations: %d \n Run number: %d \n The distance of the best tour = %d\n',numGen, runs, resultStruct.minDist);
        
    end
    i = i + 1;
end

% Implement your plotting here, using the average and confidence interval results:
% plots ...

% Calculating CIs
lower = zeros(1,20);
upper = zeros(1,20);
meanValue = zeros(1,20);
for i=1:20
    x = data(i,1:end); 
    SEM = std(x)/sqrt(length(x));               % Standard Error
    ts = tinv([0.025  0.975],length(x)-1);      % T-Score
    CI = mean(x) + ts*SEM;                      % Confidence Intervals
    lower(1,i) = CI(1);
    upper(1,i) = CI(2);
    meanValue(1,i) = mean(x);
end

% Plot

plot(meanValue,'*');
hold on
plot(lower);
hold on
plot(upper);
legend('Mean','Lower bound','Upper bound')
title('Average distance of the best tour')
xlabel('Data sets')
ylabel('Distance')
