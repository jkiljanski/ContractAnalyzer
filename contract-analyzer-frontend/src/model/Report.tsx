class Report {
    id: string;
    result: string;
    body: string;
    nameOfCheck: string;
    timestamp: string;
    userName: string;

    constructor(id: string, result: string, body: string, nameOfCheck: string, timestamp: string, userName: string) {
        this.id = id;
        this.result = result;
        this.body = body;
        this.nameOfCheck = nameOfCheck;
        this.timestamp = timestamp;
        this.userName = userName;
    }

}

export default Report;