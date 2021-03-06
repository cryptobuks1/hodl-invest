import React, {Component} from 'react';
import './TransactionHistory.css';
import tinydate from 'tinydate';

class TransactionHistory extends Component {

    constructor(props) {
        super(props);
        this.state = {
            transactions: []//Array of elements
        };
    }

    renderTransactions() {
        let transactions = this.props.transactions;
        let transactionArray = [];

        for(let i = transactions.length-1; i >=0; --i) {
            let currentTransaction = transactions[i];

            //Money handling
            let coin = currentTransaction.cryptocoin.ticker;
            let usdAmount = currentTransaction.cryptocoin.price;
            let coinAmount = currentTransaction.amount;
            let transactionType = currentTransaction.transactionType;
            let totalAmount = usdAmount * coinAmount;
            if(transactionType === 'BUY') {
                coinAmount = '+' + coinAmount + ' ' + coin.toUpperCase();
                usdAmount = '-$' + totalAmount.toFixed(2);
            } else {
                coinAmount = '-' + coinAmount + ' ' + coin.toUpperCase();
                usdAmount = '+$' + totalAmount.toFixed(2);
            }

            //Time handling
            let timeStamp = currentTransaction.timestamp;
            let stamp = tinydate('{MM}/{DD}/{YY}');
            let dateObject = new Date(timeStamp);
            let date = stamp(dateObject);
            stamp = tinydate('{HH}:{mm}');
            let time = stamp(dateObject);
            transactionArray.push(TransactionHistory.getTransactionRow(coinAmount,usdAmount,date,time))
        }
        return transactionArray;
    }

    static getTransactionRow(coinAmount,usdAmount,date,time) {
        return <tr><th>{date}</th><th>{time}</th><th>{coinAmount}</th><th>{usdAmount}</th></tr>
    }

    render() {
        return (
            <div className="transaction-history" >
                <div className="maxwindow">
                    <table>
                        <tr>
                            <th>Date</th>
                            <th>Time</th>
                            <th>Crypto</th>
                            <th>USD</th>
                        </tr>
                        {this.renderTransactions()}
                    </table>
                </div>
            </div>
        )
    }
}

export default TransactionHistory;
