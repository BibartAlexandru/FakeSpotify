package com.example.fakespotify;

public abstract class State {
    public StateType name ;

    State(StateType name)
    {
        this.name = name ;
    }
    public void OnEnter()
    {

    }

    public void WhileRunning()
    {

    }

    public void OnLeave()
    {

    }
}

