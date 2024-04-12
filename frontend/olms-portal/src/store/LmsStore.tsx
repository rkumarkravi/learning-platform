// store.ts
import { configureStore } from '@reduxjs/toolkit'
import userProfileSlice from './newway/user-profile-slice';

const store = configureStore({
    reducer: {userProfile:userProfileSlice},
  })

export default store;
