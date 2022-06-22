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
var anim;
(function (anim) {
    var Scheduler = /** @class */ (function () {
        function Scheduler() {
            this.runningProcesses = [];
            this.intervals = [];
        }
        Scheduler.prototype.run = function (process, sprite, endCallback, callbackContext) {
            process.setTarget(sprite);
            process.setEndCallback(endCallback, callbackContext);
            process.run();
        };
        Scheduler.prototype.pause = function () {
            for (var _i = 0, _a = this.runningProcesses; _i < _a.length; _i++) {
                var process = _a[_i];
                process.pause();
            }
            for (var _b = 0, _c = this.intervals; _b < _c.length; _b++) {
                var interval = _c[_b];
                interval.delay.pause();
            }
        };
        Scheduler.prototype.resume = function () {
            for (var _i = 0, _a = this.runningProcesses; _i < _a.length; _i++) {
                var process = _a[_i];
                process.resume();
            }
            for (var _b = 0, _c = this.intervals; _b < _c.length; _b++) {
                var interval = _c[_b];
                interval.delay.resume();
            }
        };
        Scheduler.prototype.stop = function () {
            this.pause();
            this.runningProcesses = [];
        };
        Scheduler.prototype.addRunningProcess = function (process) {
            for (var _i = 0, _a = this.runningProcesses; _i < _a.length; _i++) {
                var currProc = _a[_i];
                if (currProc.id === process.id)
                    return;
            }
            this.runningProcesses.push(process);
        };
        Scheduler.prototype.removeRunningProcess = function (process) {
            for (var i = 0; i < this.runningProcesses.length; i++) {
                if (this.runningProcesses[i].id == process.id) {
                    this.runningProcesses.splice(i, 1);
                }
            }
        };
        Scheduler.prototype.setTimeOut = function (duration, callback, context) {
            var delay = new Delay();
            delay.setTarget(new PIXI.Sprite());
            delay.setEndCallback(callback, context);
            delay.setDuration(duration);
            delay.run();
            return delay;
        };
        Scheduler.prototype.clearTimeOut = function (delay) {
            delay.stop();
        };
        Scheduler.prototype.setInterval = function (duration, callback, context) {
            var delay = new Delay().setDuration(duration);
            delay.setDuration(duration);
            this.intervals.push({
                delay: delay,
                callback: callback,
                context: context
            });
            delay.setEndCallback(this.nextInterval, this.intervals[this.intervals.length - 1]);
            delay.setTarget(new PIXI.Sprite());
            delay.run();
            return delay;
        };
        Scheduler.prototype.nextInterval = function () {
            this.callback.call(this.context);
            if (!this.delay.isStopped)
                this.delay.run();
        };
        Scheduler.prototype.clearInterval = function (delay) {
            delay.stop();
            for (var i = 0; i < this.intervals.length; i++) {
                if (this.intervals[i].delay.id == delay.id) {
                    this.intervals.splice(i, 1);
                }
            }
        };
        return Scheduler;
    }());
    anim.Scheduler = Scheduler;
    anim.scheduler = new Scheduler();
    var animPaused = false;
    var epsilon = 0.001;
    anim.Types = {
        Process: "process",
        Delay: "delay",
        Sequence: "sequence",
        Parallel: "parallel",
        FadeTo: "fadeTo",
        MoveTo: "moveTo",
        MoveBy: "moveBy",
        RotateTo: "rotateTo",
        RotateBy: "rotateBy",
        ScaleTo: "scaleTo",
        ScaleBy: "scaleBy"
    };
    var Process = /** @class */ (function () {
        function Process() {
            var _this = this;
            this.type = anim.Types.Process;
            this.duration = 1000;
            this.currTime = 0;
            this.requestAnimCallback = function () {
                var currTime = performance.now();
                var delta = currTime - _this.prevTimestamp;
                if (_this.currTime + delta >= _this.duration) {
                    delta = _this.duration - _this.currTime;
                }
                else {
                    _this.currRequestId = requestAnimationFrame(_this.requestAnimCallback);
                }
                _this.currTime += delta;
                _this.prevTimestamp = currTime;
                _this.update(delta);
            };
            this.id = Math.random() * 10000;
            this.isStopped = false;
        }
        /**
         * @param {number} duration of animation in milliseconds
         * @returns {bc.Process}
         */
        Process.prototype.setDuration = function (duration) {
            this.duration = duration;
            return this;
        };
        Process.prototype.setTarget = function (target) {
            this.target = target;
        };
        Process.prototype.setEndCallback = function (callback, callbackContext) {
            this.endCallback = callback;
            this.endCallbackContext = callbackContext;
        };
        Process.prototype.run = function () {
            if (!this.target) {
                console.warn("Anim " + this.type + " target not specified");
                return;
            }
            this.isStopped = false;
            this.currTime = 0;
            this.prevTimestamp = performance.now();
            anim.scheduler.addRunningProcess(this);
            if (!animPaused)
                this.currRequestId = requestAnimationFrame(this.requestAnimCallback);
        };
        ;
        Process.prototype.pause = function () {
            cancelAnimationFrame(this.currRequestId);
        };
        Process.prototype.resume = function () {
            this.prevTimestamp = performance.now();
            this.currRequestId = requestAnimationFrame(this.requestAnimCallback);
        };
        Process.prototype.stop = function () {
            this.isStopped = true;
            cancelAnimationFrame(this.currRequestId);
            anim.scheduler.removeRunningProcess(this);
        };
        Process.prototype.update = function (delta) { };
        return Process;
    }());
    anim.Process = Process;
    var Delay = /** @class */ (function (_super) {
        __extends(Delay, _super);
        function Delay() {
            var _this = _super.call(this) || this;
            _this.type = anim.Types.Delay;
            return _this;
        }
        Delay.prototype.update = function (delta) {
            if (this.currTime == this.duration) {
                if (this.endCallback)
                    this.endCallback.call(this.endCallbackContext, this.target);
                anim.scheduler.removeRunningProcess(this);
            }
        };
        return Delay;
    }(Process));
    anim.Delay = Delay;
    var Sequence = /** @class */ (function (_super) {
        __extends(Sequence, _super);
        function Sequence(processes) {
            var _this = _super.call(this) || this;
            _this.subProcesses = [];
            _this.increaseCounter = function () {
                _this.currentRunningIndex++;
                _this.next();
            };
            _this.type = anim.Types.Sequence;
            _this.subProcesses = processes;
            return _this;
        }
        Sequence.prototype.run = function () {
            this.currentRunningIndex = 0;
            this.next();
        };
        Sequence.prototype.next = function () {
            var currProcess = this.subProcesses[this.currentRunningIndex];
            if (currProcess) {
                currProcess.setTarget(this.target);
                currProcess.setEndCallback(this.increaseCounter);
                currProcess.run();
            }
            else if (this.endCallback) {
                this.endCallback.call(this.endCallbackContext, this.target);
            }
        };
        return Sequence;
    }(Process));
    anim.Sequence = Sequence;
    var Parallel = /** @class */ (function (_super) {
        __extends(Parallel, _super);
        function Parallel(processes) {
            var _this = _super.call(this) || this;
            _this.subProcesses = [];
            _this.endedProcesses = 0;
            _this.checkEnd = function () {
                _this.endedProcesses++;
                if (_this.endedProcesses === _this.subProcesses.length && _this.endCallback) {
                    _this.endCallback.call(_this.endCallbackContext, _this.target);
                }
            };
            _this.type = anim.Types.Parallel;
            _this.subProcesses = processes;
            return _this;
        }
        Parallel.prototype.run = function () {
            for (var _i = 0, _a = this.subProcesses; _i < _a.length; _i++) {
                var process = _a[_i];
                process.setTarget(this.target);
                process.setEndCallback(this.checkEnd);
                process.run();
            }
        };
        return Parallel;
    }(Process));
    anim.Parallel = Parallel;
    var FadeTo = /** @class */ (function (_super) {
        __extends(FadeTo, _super);
        function FadeTo(to) {
            var _this = _super.call(this) || this;
            _this.type = anim.Types.FadeTo;
            _this.to = to;
            return _this;
        }
        FadeTo.prototype.run = function () {
            _super.prototype.run.call(this);
            this.fadeDiffPerMilliSec = (this.to - this.target.alpha) / this.duration;
        };
        FadeTo.prototype.update = function (delta) {
            this.target.alpha += delta * this.fadeDiffPerMilliSec;
            if (this.currTime == this.duration) {
                if (this.endCallback)
                    this.endCallback.call(this.endCallbackContext, this.target);
                anim.scheduler.removeRunningProcess(this);
            }
        };
        return FadeTo;
    }(Process));
    anim.FadeTo = FadeTo;
    var MoveTo = /** @class */ (function (_super) {
        __extends(MoveTo, _super);
        function MoveTo(to) {
            var _this = _super.call(this) || this;
            _this.to = new PIXI.Point(to.x, to.y);
            return _this;
        }
        MoveTo.prototype.run = function () {
            _super.prototype.run.call(this);
            this.dir = new PIXI.Point((this.to.x - this.target.x) / this.duration, (this.to.y - this.target.y) / this.duration);
        };
        MoveTo.prototype.update = function (delta) {
            this.target.x += this.dir.x * delta;
            this.target.y += this.dir.y * delta;
            if (this.currTime == this.duration) {
                if (this.endCallback)
                    this.endCallback.call(this.endCallbackContext, this.target);
                anim.scheduler.removeRunningProcess(this);
            }
        };
        return MoveTo;
    }(Process));
    anim.MoveTo = MoveTo;
    var MoveBy = /** @class */ (function (_super) {
        __extends(MoveBy, _super);
        function MoveBy(by) {
            var _this = _super.call(this) || this;
            _this.by = new PIXI.Point(by.x, by.y);
            return _this;
        }
        MoveBy.prototype.run = function () {
            _super.prototype.run.call(this);
            this.dir = new PIXI.Point(this.by.x / this.duration, this.by.y / this.duration);
        };
        MoveBy.prototype.update = function (delta) {
            this.target.x += this.dir.x * delta;
            this.target.y += this.dir.y * delta;
            if (this.currTime == this.duration) {
                if (this.endCallback)
                    this.endCallback.call(this.endCallbackContext, this.target);
                anim.scheduler.removeRunningProcess(this);
            }
        };
        return MoveBy;
    }(Process));
    anim.MoveBy = MoveBy;
    var ScaleTo = /** @class */ (function (_super) {
        __extends(ScaleTo, _super);
        function ScaleTo(x, y) {
            var _this = _super.call(this) || this;
            _this.to = new PIXI.Point();
            _this.to.set(x, y ? y : x);
            _this.amount = new PIXI.Point();
            return _this;
        }
        ScaleTo.prototype.run = function () {
            _super.prototype.run.call(this);
            this.amount.set((this.to.x - this.target.scale.x) / this.duration, (this.to.y - this.target.scale.y) / this.duration);
        };
        ScaleTo.prototype.update = function (delta) {
            this.target.scale.x += this.amount.x * delta;
            this.target.scale.y += this.amount.y * delta;
            if (this.currTime == this.duration) {
                if (this.endCallback)
                    this.endCallback.call(this.endCallbackContext, this.target);
                anim.scheduler.removeRunningProcess(this);
            }
        };
        return ScaleTo;
    }(Process));
    anim.ScaleTo = ScaleTo;
    var ScaleBy = /** @class */ (function (_super) {
        __extends(ScaleBy, _super);
        function ScaleBy(by) {
            var _this = _super.call(this) || this;
            _this.by = new PIXI.Point(by.x, by.y);
            _this.amount = new PIXI.Point();
            return _this;
        }
        ScaleBy.prototype.run = function () {
            _super.prototype.run.call(this);
            this.amount.set(this.by.x / this.duration, this.by.y / this.duration);
        };
        ScaleBy.prototype.update = function (delta) {
            this.target.scale.x += this.amount.x * delta;
            this.target.scale.y += this.amount.y * delta;
            if (this.currTime == this.duration) {
                if (this.endCallback)
                    this.endCallback.call(this.endCallbackContext, this.target);
                anim.scheduler.removeRunningProcess(this);
            }
        };
        return ScaleBy;
    }(Process));
    anim.ScaleBy = ScaleBy;
    var RotateTo = /** @class */ (function (_super) {
        __extends(RotateTo, _super);
        function RotateTo(angle) {
            var _this = _super.call(this) || this;
            _this.to = angle;
            return _this;
        }
        RotateTo.prototype.run = function () {
            _super.prototype.run.call(this);
            this.amount = (this.to - this.target.rotation) / this.duration;
        };
        RotateTo.prototype.update = function (delta) {
            this.target.rotation += this.amount * delta;
            if (this.currTime == this.duration) {
                if (this.endCallback)
                    this.endCallback.call(this.endCallbackContext, this.target);
                anim.scheduler.removeRunningProcess(this);
            }
        };
        return RotateTo;
    }(Process));
    anim.RotateTo = RotateTo;
    var RotateBy = /** @class */ (function (_super) {
        __extends(RotateBy, _super);
        function RotateBy(angle) {
            var _this = _super.call(this) || this;
            _this.by = angle;
            return _this;
        }
        RotateBy.prototype.run = function () {
            _super.prototype.run.call(this);
            this.amount = this.by / this.duration;
        };
        RotateBy.prototype.update = function (delta) {
            this.target.rotation += this.amount * delta;
            if (this.currTime == this.duration) {
                if (this.endCallback)
                    this.endCallback.call(this.endCallbackContext, this.target);
                anim.scheduler.removeRunningProcess(this);
            }
        };
        return RotateBy;
    }(Process));
    anim.RotateBy = RotateBy;
})(anim || (anim = {}));
//# sourceMappingURL=anim.js.map