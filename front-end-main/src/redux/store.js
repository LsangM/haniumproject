import { createStore, combineReducers } from 'redux';
import tokenReducer from './reducers';

const rootReducer = combineReducers({
  token: tokenReducer,
});

const store = createStore(rootReducer);

export default store;
