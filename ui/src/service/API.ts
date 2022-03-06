import axios from 'axios';

class API {
    handleError(error: string) {
        console.error(error);
    }

    get(url: string) {
        return axios.get(url).catch(this.handleError);
    }

    post(url: string, value: object) {
        return axios.post(url, value).catch(this.handleError);
    }
}

export default new API();
