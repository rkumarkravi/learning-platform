import { SET_USER_PROFILE } from "./LmsActionTypes";

export const setUserProfile = (profile) => ({
  type: SET_USER_PROFILE,
  payload: profile,
});
