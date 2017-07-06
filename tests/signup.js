var webdriverio = require('webdriverio')
var assert = require('assert')
var generator = require('generate-password')
var options = { desiredCapabilities: { browserName: 'chrome' } }

var clientA = webdriverio.remote(options)
var clientB = webdriverio.remote(options)
var clients = [clientA, clientB]
clients.forEach (function(client) {
  client.on('error', function(e) {
    console_log("["+e.body.value.class+"] " + e.body.value.message)
  })
})
process.on('uncaughtException', function (exception) {
  console_log(exception)
})

var email = null
var password = generator.generate({
    length: 10,
    numbers: true
})


function console_log(s) {
  //console.log(s)
  process.stdout.write('> '+(new Date()) + ": " + s + "\n")
}

function beginTest() {
  try {
    initFakeEmail()
    //signup
    //waitForSignupEmail
    //verifyAccountConfirmed
  } catch(ex) {
    console_log(ex.message)
  } finally {
    clients.forEach(function(client) {
      client.end()
      client.close()
    })
  }
}

var mailWindowHandle = null;

function initFakeEmail() {
  var name_token = generator.generate({
      length: 16,
      numbers: false
  })
  clientA
      .init()
      .windowHandlePosition({x: 0, y: 0})
      .url('https://www.mailinator.com/')
      .getTitle()
      .setValue('#inboxfield',name_token)
      .click('button*=Go')
      /*.sleep(1000).windowHandles()
      .then(function(handles) {
        mailWindowHandle = handles[0]
        console_log("mailWindowHandle: " + mailWindowHandle)
      })*/
      .then(function(text) {
        console_log('got')
        email = name_token + "@mailinator.com"
        console_log(email)
        signup()
      })
}

function signup() {
  clientB
      .init()
      .windowHandlePosition({x: 500, y: 0})
      .url('https://notarize.com/')
      .click('.started-button')
      .setValue('input[data-automation-id="customer-email-field"]', email)
      .setValue('input[data-automation-id="set-password-field"]', password)
      .click('label[for="consent-checkbox"]')
      .click('button[data-automation-id="submit-button"]')
      .then(function(title) {
        waitForSignupEmail()
      })
}

function waitForSignupEmail() {
  clientA
      .waitUntil(function() {
        return clientA.isVisible('div.innermail')
      }, 3 * 60 * 1000,'Failed to find incoming email signup message.')
      .click('div.innermail')
      .waitForExist('#publicshowmaildivcontent')
      .frame("publicshowmaildivcontent")
      .waitForExist('a*=Verify', 30 * 1000)
      .click('a*=Verify') // opens a new tab, focuses on new tab
      /*.sleep(1000).windowHandles()
      .then(function(handles) {
        console_log("handles.length: " + handles.length)
        handles.forEach(function(handle) {
          if(!(handle===mailWindowHandle)) {
            console_log("handle: " + handle)
          }
        })
      })*/
      .then(function() {
        verifyAccountConfirmed(clientB)
      })
      /*.waitForExist('.Button-success', 30 * 1000)
      .click('.Button-success') // should change this to data-automation-id='continue-button'
      .then(function() {
        verifyAccountConfirmed(clientA) // clientB session still retains that account hasn't been verified
      })*/
}

function verifyAccountConfirmed(client) {
  client
    .click('span=Account')
    .click('li[data-automation-id="settings-button"]')
    .then(function() {
      var searchFor = "must verify your current email";
      var text = clientB.getText('*='+searchFor)
      assert((text+"").indexOf(searchFor) < 0,'verification warning is gone')
    })
}


beginTest()
