import { createSlice } from "@reduxjs/toolkit";

const authSlice = createSlice({
    name: 'auth',
    initialState: { token: localStorage.getItem('token') },
    reducers: {
        login(state, action) {
            const token = action.payload
            localStorage.setItem('token', token)
            state.token = token
            console.log(localStorage.getItem('token'))
        },
        logout(state) {
            state.token = undefined
            localStorage.removeItem('token')
        },
    }
})

export const authActions = authSlice.actions
export default authSlice.reducer