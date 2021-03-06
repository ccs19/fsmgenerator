%%**************************%%
%% Accept States			%%
%%**************************%%

accept(s1).
accept(s3).

%%**************************%%
%% RULES 					%%
%%**************************%%

fsa(List) :- s0(List)


%%**************************%%
%% s0   					%%
%%**************************%%

s0([]) :- accept(s0).
s0([Head | Tail]) :- s0(Head, Tail).
s0(x, List) :- s0(List).
s0(y, List) :- s1(List).


%%**************************%%
%% s1   					%%
%%**************************%%

s1([]) :- accept(s1).
s1([Head | Tail]) :- s1(Head, Tail).
s1(x, List) :- s2(List).


%%**************************%%
%% s2   					%%
%%**************************%%

s2([]) :- accept(s2).
s2([Head | Tail]) :- s2(Head, Tail).
s2(x, List) :- s2(List).
s2(y, List) :- s3(List).


%%**************************%%
%% s3   					%%
%%**************************%%

s3([]) :- accept(s3).
s3([Head | Tail]) :- s3(Head, Tail).
s3(x, List) :- s3(List).
s3(z, List) :- s4(List).


%%**************************%%
%% s4   					%%
%%**************************%%

s4([]) :- accept(s4).
s4([Head | Tail]) :- s4(Head, Tail).
s4(x, List) :- s4(List).
s4(a, List) :- s1(List).

%%**************************%%
%% queries 					%%
%%**************************%%

good :- fsa([x,x,x,x,x,y,x,x,x,y,x,x,x,z,x,x,x,a]).
bad :- fsa([x,x,x,x,x,x,x,x,x,x,x,x,x,x,y,x,x,x,y,x,x,x,y,z,x,x,z,x,a]).







