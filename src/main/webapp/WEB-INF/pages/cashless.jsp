<!DOCTYPE html>
<html>
   <head>
      <title>Cashless API</title>
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <style>
         body {
         font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
         background-color: #004A94;
         color: white;
         }
         .container {
         text-align: center;
         margin-top: 5em;
         min-width: 70%;
         }
         .link-container {
         padding: 1em;
         width: 80%;
         background-color: white;
         border-radius: 6px;
         box-shadow: 0px 2px 10px 0px #064079;
         }
         a {
         text-decoration: none;
         font-weight: 700;
         color: white;
         }
         .status {
         background-color: #e2e2e2;
         padding: 2px 8px;
         border-radius: 12px;
         color: #5f5f5f;
         }
         img {
         width: 80px;
         height: 80px;
         margin: 0.5em;
         border-radius: 16px;
         }
      </style>
   </head>
   <body>
      <a href="/">HOME</a>
      <div class="container">
         <div style="display: flex;justify-content: center;">
            <div class="link-container">
               <h2 style="color: #004A94">Pay by Bank Apps</h2>
               <div>
                  <div style="margin-bottom: 5px;">
                     <div>
                        <a href="#">
                        <img src="/images/bay.jpg" alt="KMA">
                        </a>
                        <a href="#">
                        <img src="/images/kbank.jpg" alt="Kplus">
                        </a>
                     </div>
                     <div>
                        <a id="scbdeeplink" href="${scbdeeplink}">
                            <img src="/images/scb.jpg" alt="SCB Easy">
                        </a>
                        <a href="#">
                            <img src="/images/ktb.jpg" alt="KTB">
                        </a>
                     </div>
                  </div>
                  <a href="/scb/payments/f4147edd-d697-4ecd-8483-1439c6cdc9a7" target="_blank" style="color:#004A94">
                      <small class="status">status</small>
                  </a>
               </div>
            </div>
         </div>
         <div style="margin-top: 1em;font-size: 0.7em;">
            Service Provided by Cashless API
         </div>
         <div style="margin-top: 1em;font-size: 0.7em;">
            deeplink_id: f4147edd-d697-4ecd-8483-1439c6cdc9a7
         </div>
      </div>
   </body>
</html>