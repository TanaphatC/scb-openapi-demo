<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width">
    <title>Cashless API</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
      integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <style>
      body {
        font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
        background-color: #004A94;
        color: white;
      }
      h1 {
        font-size: 2em;
      }
      .form-container {
        background-color: white;
        color: #004A94;
        padding: 1.5em;
        border-radius: 6px;
      }
      small {
        color: grey;
      }
      .section {
        padding: 1em;
        border: 1px solid #ced4da;
        border-radius: 5px;
      }
      .btn-primary {
        background-color: #004A94;
        color: white;
      }
    </style>
  </head>
  <body>
    <div class="text-center">
      <h2 class="mt-5">Cashless API</h2>
      <h4>Pay by Bank Apps</h4>
    </div>
    <div class="container-fluid">
      <div class="row justify-content-center">
        <div class="col-10 col-md-8 form-container mt-3 mb-5">
          <div class="text-right">App name: MyAllianz</div>
          <form>
            <div class="form-group">
              <label for="bankInput">Banks</label>
              <input type="text" class="form-control"
                id="bankInput" placeholder="SCB, KBANK" value="SCB" readonly>
            </div>
            <div class="form-group">
              <label for="txnSubTypeInput">Transaction Sub Type</label>
              <input type="text" class="form-control" id="txnSubTypeInput"
                value="BP, CCFA, CCIPP" readonly>
            </div>
            <div class="form-group">
              <label for="billAmountInput">Bill Amount</label>
              <input type="text" class="form-control" id="billAmountInput"
                value="100.00">
            </div>
            <div class="form-group">
              <label for="creditAmountInput">Credit Amount</label>
              <input type="text" class="form-control" id="creditAmountInput"
                value="100.00">
            </div>
            <div class="section">
              <div class="form-group">
                <label for="installmentAmountInput">Installment Amount</label>
                <input type="text" class="form-control" id="installmentAmountInput"
                  value="100.00">
              </div>
              <div class="form-group">
                <label for="tenorInput">Tenor</label>
                <input type="text" class="form-control" id="tenorInput"
                  value="12" readonly>
              </div>
            </div>
            <button type="submit" class="w-100 btn btn-primary mt-3" id="cp">
              Cashless Payment
            </button>
            <button type="button" class="w-100 btn btn-primary mt-3" id="qr">
              QR Code
            </button>
          </form>
          <form id="qrcode_submit" action="/qrcode" method="post">
            <input type="hidden" id="qrcode" name="qrcode">
          </form>

        </div>
      </div>
    </div>
    <script>
      const formBtn = document.querySelector('#cp');
      const qrBtn = document.querySelector('#qr');

      function getValue(query) {
        return document.querySelector(query).value;
      }

      formBtn.addEventListener('click', e => {
        e.preventDefault();
        formBtn.disabled = true;

        fetch('/api/deeplink', {
          method: 'POST',
          mode: 'cors',
          credentials: 'same-origin',
          referrerPolicy: 'no-referrer',
          redirect: 'follow',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            "banks":[ "SCB" ],
            "transactionSubType":[ "BP", "CCFA", "CCIPP" ],
            "billPayment":{
              "paymentAmount": getValue('#billAmountInput'),
            },
            "creditCardFullAmount":{
              "paymentAmount": getValue('#creditAmountInput'),
            },
            "installmentPaymentPlan":{
              "paymentAmount": getValue('#installmentAmountInput'),
              "tenor": getValue('#tenorInput'),
            }
          }),
        })
        .then(response => {
            return response.json()
        })
        .then(json => {
            console.log(json)
            var scbDeeplink = json.scbDeeplink
            window.location.href = scbDeeplink
        })
        .finally(() => {
            formBtn.disabled = false;
        });
      });

      qrBtn.addEventListener('click', e => {
        e.preventDefault();
        qrBtn.disabled = true;

        fetch('/api/generateQRCode', {
          method: 'POST',
          mode: 'cors',
          credentials: 'same-origin',
          referrerPolicy: 'no-referrer',
          redirect: 'follow',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            "qrType": "PPCS",
            "amount": getValue('#billAmountInput'),
          }),
        })
        .then(response => {
            return response.json()
        })
        .then(json => {
            console.log(json)
            var qrcode = json.scbDeeplink
            document.querySelector('#qrcode').value = qrcode;
            document.getElementById("qrcode_submit").submit();
        })
        .finally(() => {
            qrBtn.disabled = false;
        });
      });

    </script>
  </body>
</html>
