import { useDispatch } from "react-redux"
import { useHistory } from "react-router-dom";
import { authActions } from "../store/authSlice";

const style = {
    backgroundColor: 'brown',
    fontSize: '12',
    border:'1'
}

const Logout = () => {

    const dispatch = useDispatch()
    const history = useHistory()

    const logoutHandler = () =>{
        dispatch(authActions.logout())
        history.replace('login')
    }
    return (
        <button onClick={logoutHandler} style={style}>Logout</button>
    )
}

export default Logout