var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
/// <reference path="../../pixi.js.d.ts"/>
/// <reference path="../../anim/anim.d.ts"/>
/// <reference path="../../m3wThings.d.ts"/>
var ResultScreen = /** @class */ (function (_super) {
    __extends(ResultScreen, _super);
    function ResultScreen(screenWidth, screenHeight) {
        var _this = _super.call(this) || this;
        _this.messageCallback = function (message) {
            _this.resultMessage.text = message;
        };
        _this.score = 0;
        var addition = 100;
        if (screenWidth <= 700)
            addition = 100;
        var graphics = new PIXI.Graphics();
        var baseW = screenWidth / 2 + addition;
        var baseH = screenWidth / 2 / 1.6667 + addition / 1.6667;
        _this.width = baseW;
        _this.height = baseH;
        _this.position.set(screenWidth / 4 - addition / 2, screenHeight / 2 - screenWidth / 2 / 1.6667 / 2 - addition / 2);
        // shadow
        graphics.beginFill(0x402b21, 0.5);
        graphics.drawRoundedRect(5, 5, baseW, baseH, 5);
        // border
        graphics.beginFill(0x0);
        graphics.drawRoundedRect(-1, -1, baseW + 2, baseH + 2, 5);
        // white background
        graphics.beginFill(0xffffff);
        graphics.drawRoundedRect(0, 0, baseW, baseH, 5);
        graphics.endFill();
        _this.addChild(graphics);
        _this.gameEnded = new PIXI.Text(__("game ended"), { fontSize: 14 * screenWidth / 800 });
        _this.gameEnded.anchor.set(0.5, 0.5);
        _this.gameEnded.position.set(baseW / 2, baseH / 9);
        _this.addChild(_this.gameEnded);
        _this.yourResultIs = new PIXI.Text(__("your result is") + ":", { fontSize: 26 * screenWidth / 800, fontWeight: "bold" });
        _this.yourResultIs.anchor.set(0.5, 0.5);
        _this.yourResultIs.position.set(baseW / 2, baseH / 3.5);
        graphics.addChild(_this.yourResultIs);
        _this.scoreLabel = new PIXI.Text("0", { fontSize: 26 * screenWidth / 800, fontWeight: "bold" });
        _this.scoreLabel.anchor.set(0.5, 0.5);
        _this.scoreLabel.position.set(baseW / 2, baseH / 2.4);
        _this.addChild(_this.scoreLabel);
        _this.resultMessage = new PIXI.Text("", {
            fontSize: 14 * screenWidth / 800,
            breakWords: true,
            wordWrap: true,
            wordWrapWidth: screenWidth / 2
        });
        _this.resultMessage.anchor.set(0.5, 0);
        _this.resultMessage.position.set(baseW / 2, baseH / 2);
        _this.addChild(_this.resultMessage);
        // play button
        baseW = baseW * 0.6;
        baseH = baseH / 6;
        _this.button = new Button(baseW, baseH, __("play again"), 22 * screenWidth / 800);
        _this.button.position.set(_this.width / 2 - baseW / 2, _this.height / 1.45);
        _this.button.on("click", function () {
            document.getElementsByClassName("mw-game-button--start")[0].click();
        });
        _this.addChild(_this.button);
        return _this;
    }
    ResultScreen.prototype.setScore = function (score) {
        this.score = score;
        this.currScore = 0;
        this.intervalId = anim.scheduler.setInterval(20, this.increase, this);
    };
    ResultScreen.prototype.increase = function () {
        this.currScore += 15;
        if (this.currScore >= this.score) {
            this.currScore = this.score;
            anim.scheduler.clearInterval(this.intervalId);
        }
        this.scoreLabel.text = this.currScore.toString();
    };
    ResultScreen.prototype.setReplayCallback = function (replayCallback, ctx) {
    };
    return ResultScreen;
}(PIXI.Container));
var Button = /** @class */ (function (_super) {
    __extends(Button, _super);
    function Button(w, h, text, fontSize) {
        var _this = _super.call(this) || this;
        _this.width = _this.w = w;
        _this.height = _this.h = h;
        _this.graphics = new PIXI.Graphics();
        _this.addChild(_this.graphics);
        _this.redraw(false);
        var label = new PIXI.Text(text, { fontSize: fontSize, fontWeight: "bold" });
        label.anchor.set(0.5, 0.5);
        label.position.set(w / 2, h / 2);
        _this.addChild(label);
        _this.interactive = true;
        _this.on("mouseover", function () { return _this.redraw(true); }, _this);
        _this.on("mouseout", function () { return _this.redraw(false); }, _this);
        return _this;
    }
    Button.prototype.redraw = function (highLighted) {
        this.graphics.beginFill(0x0);
        this.graphics.drawRoundedRect(0, 0, this.w, this.h, 5);
        this.graphics.beginFill(highLighted ? 0xd1e7f2 : 0xc1d7e2);
        this.graphics.drawRoundedRect(1, 1, this.w - 2, this.h - 2, 5);
        this.graphics.endFill();
    };
    return Button;
}(PIXI.Container));
