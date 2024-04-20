// store.ts
import { configureStore } from "@reduxjs/toolkit";
import userProfileSlice from "./newway/user-profile-slice";
import keyValueSlice from "./newway/key-value-slice";

const store = configureStore({
  reducer: { userProfile: userProfileSlice, keyValue: keyValueSlice },
});

export default store;
