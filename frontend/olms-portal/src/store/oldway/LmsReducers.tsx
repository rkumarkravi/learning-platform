import { combineReducers } from "redux";
import { SET_USER_PROFILE } from "./LmsActionTypes";

const initialState = {
  userProfile: null,
};
export type RootState = ReturnType<typeof rootReducer>;

const userProfileReducer = (state = initialState, action: {type:string,payload:any}) => {
  switch (action.type) {
    case SET_USER_PROFILE:
      return {
        ...state,
        userProfile: action.payload,
      };
    default:
      return state;
  }
};


const rootReducer = combineReducers({
  userProfile: userProfileReducer,
  // Add other reducers here if needed
});

export default rootReducer;
