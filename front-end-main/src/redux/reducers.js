const initialState = {
    token: localStorage.getItem('token') || null,
};

const tokenReducer = (state = initialState, action) => {
    switch (action.type) {
        case 'SET_TOKEN':
            localStorage.setItem('token', action.payload);
            return { ...state, token: action.payload };
        case 'CLEAR_TOKEN':
            localStorage.removeItem('token');
            return { ...state, token: null };
        default:
            return state;
    }
};

export default tokenReducer;