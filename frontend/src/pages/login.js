import { useRef, useState } from "react";
import { useDispatch } from "react-redux"
import { useHistory } from "react-router-dom";
import { authActions } from "../store/authSlice";
import '../css/login.css'

const Login = () => {
    const emailRef = useRef(null)
    const passwordRef = useRef(null)
    const dispatch = useDispatch()
    const history = useHistory()

    const [clientType, setClientType] = useState('/customer')

    const handleChangeClientType = (e) => {
        console.log(e.target.value)
        setClientType(e.target.value)
    }

    const performLogin = async (email, password) => {

        try {
            const response = await fetch('http://localhost:8080/api' + clientType + '/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ email, password })
            })
            if (!response.ok) {
                if (response.status === 401) {
                    throw new Error("Wrong user name or password")
                }
            }

            const data = await response.text()

            if (response.ok) {
                return data
            }
            return undefined
        } catch (error) {
            console.log(error.message)
            window.alert(error.message)
            var form = document.getElementById('loginForm');
            form.reset();
        }
    }

    const handleLoginSubmission = async (event) => {
        event.preventDefault()

        const email = emailRef.current.value
        const password = passwordRef.current.value

        localStorage.setItem('clientType', clientType)

        const loginData = await performLogin(email, password)

        if (loginData) {
            console.log(loginData)
            dispatch(authActions.login(loginData))
            history.replace(clientType + '/my_coupons')
        }
    }

    return (
        <div class="container">
            <div class="top">
                <h1 id="title" class="hidden"><span id="logo">Welcome </span></h1>
            </div>
            <form id='loginForm' onSubmit={handleLoginSubmission}>
                <h3>Email:</h3>
                <input type="email" ref={emailRef} />
                <h3>Password:</h3>
                <input type="password" ref={passwordRef} />
                <br />
                <br />
                <div className="radio">
                    <label>
                        <input type="radio" value='/customer'
                            onChange={handleChangeClientType} checked={clientType === '/customer'} />
                        customer
                    </label>
                </div>
                <div className="radio">
                    <label>
                        <input type="radio" value='/company'
                            onChange={handleChangeClientType} checked={clientType === '/company'} />
                        company
                    </label>
                </div>
                <br />
                <br />
                <button class='button' type="submit" >login</button>
            </form>
        </div>
    )
}

export default Login;
