import { createSlice } from '@reduxjs/toolkit';

// Initial state for the key-value pairs
const initialState = {
  data: {}, // Object to store key-value pairs
};

// Create a slice for managing key-value pairs
const keyValueSlice = createSlice({
  name: 'keyValue',
  initialState,
  reducers: {
    // Action to set a key-value pair
    setValue(state, action) {
      const { key, value } = action.payload;
      state.data[key] = value;
    },
    // Action to remove a key-value pair
    removeValue(state, action) {
      const { key } = action.payload;
      delete state.data[key];
    },
    // Action to clear all key-value pairs
    clearValues(state) {
      state.data = {};
    },
  },
});

// Export action creators
export const { setValue, removeValue, clearValues } = keyValueSlice.actions;

// Export reducer
export default keyValueSlice.reducer;
