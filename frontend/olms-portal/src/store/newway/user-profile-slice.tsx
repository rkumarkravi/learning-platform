import { createSlice,PayloadAction } from "@reduxjs/toolkit";
import { reducer } from './../../components/ui/use-toast';

interface UserState{
    value:any;
}

const initState:UserState={
    value:{}
}

const userSlice=createSlice(
    {
     name:"user",
     initialState:initState,
        reducers:{
            put(state,action){
                state.value=action.payload;
            },
            remove(state){
                state.value={};
            },
        }
    }
)

export const {put,remove}=userSlice.actions;
export default userSlice.reducer;