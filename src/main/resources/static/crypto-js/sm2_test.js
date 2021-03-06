window.sm2 = function(t) {
    function i(r) {
        if (e[r]) return e[r].exports;
        var n = e[r] = {
            i: r,
            l: !1,
            exports: {}
        };
        return t[r].call(n.exports, n, n.exports, i),
        n.l = !0,
        n.exports
    }
    var e = {};
    return i.m = t,
    i.c = e,
    i.d = function(t, e, r) {
        i.o(t, e) || Object.defineProperty(t, e, {
            configurable: !1,
            enumerable: !0,
            get: r
        })
    },
    i.n = function(t) {
        var e = t && t.__esModule ?
        function() {
            return t.
        default
        }:
        function() {
            return t
        };
        return i.d(e, "a", e),
        e
    },
    i.o = function(t, i) {
        return Object.prototype.hasOwnProperty.call(t, i)
    },
    i.p = "",
    i(i.s = 3)
} ([function(t, i, e) { (function() {
        function e(t, i, e) {
            null != t && ("number" == typeof t ? this.fromNumber(t, i, e) : null == i && "string" != typeof t ? this.fromString(t, 256) : this.fromString(t, i))
        }
        function r() {
            return new e(null)
        }
        function n(t, i, e, r, n, s) {
            for (; --s >= 0;) {
                var o = i * this[t++] + e[r] + n;
                n = Math.floor(o / 67108864),
                e[r++] = 67108863 & o
            }
            return n
        }
        function s(t, i, e, r, n, s) {
            for (var o = 32767 & i,
            h = i >> 15; --s >= 0;) {
                var u = 32767 & this[t],
                a = this[t++] >> 15,
                f = h * u + a * o;
                u = o * u + ((32767 & f) << 15) + e[r] + (1073741823 & n),
                n = (u >>> 30) + (f >>> 15) + h * a + (n >>> 30),
                e[r++] = 1073741823 & u
            }
            return n
        }
        function o(t, i, e, r, n, s) {
            for (var o = 16383 & i,
            h = i >> 14; --s >= 0;) {
                var u = 16383 & this[t],
                a = this[t++] >> 14,
                f = h * u + a * o;
                u = o * u + ((16383 & f) << 14) + e[r] + n,
                n = (u >> 28) + (f >> 14) + h * a,
                e[r++] = 268435455 & u
            }
            return n
        }
        function h(t) {
            return pi.charAt(t)
        }
        function u(t, i) {
            var e = yi[t.charCodeAt(i)];
            return null == e ? -1 : e
        }
        function a(t) {
            for (var i = this.t - 1; i >= 0; --i) t[i] = this[i];
            t.t = this.t,
            t.s = this.s
        }
        function f(t) {
            this.t = 1,
            this.s = t < 0 ? -1 : 0,
            t > 0 ? this[0] = t: t < -1 ? this[0] = t + this.DV: this.t = 0
        }
        function l(t) {
            var i = r();
            return i.fromInt(t),
            i
        }
        function c(t, i) {
            var r;
            if (16 == i) r = 4;
            else if (8 == i) r = 3;
            else if (256 == i) r = 8;
            else if (2 == i) r = 1;
            else if (32 == i) r = 5;
            else {
                if (4 != i) return void this.fromRadix(t, i);
                r = 2
            }
            this.t = 0,
            this.s = 0;
            for (var n = t.length,
            s = !1,
            o = 0; --n >= 0;) {
                var h = 8 == r ? 255 & t[n] : u(t, n);
                h < 0 ? "-" == t.charAt(n) && (s = !0) : (s = !1, 0 == o ? this[this.t++] = h: o + r > this.DB ? (this[this.t - 1] |= (h & (1 << this.DB - o) - 1) << o, this[this.t++] = h >> this.DB - o) : this[this.t - 1] |= h << o, (o += r) >= this.DB && (o -= this.DB))
            }
            8 == r && 0 != (128 & t[0]) && (this.s = -1, o > 0 && (this[this.t - 1] |= (1 << this.DB - o) - 1 << o)),
            this.clamp(),
            s && e.ZERO.subTo(this, this)
        }
        function p() {
            for (var t = this.s & this.DM; this.t > 0 && this[this.t - 1] == t;)--this.t
        }
        function y(t) {
            if (this.s < 0) return "-" + this.negate().toString(t);
            var i;
            if (16 == t) i = 4;
            else if (8 == t) i = 3;
            else if (2 == t) i = 1;
            else if (32 == t) i = 5;
            else {
                if (4 != t) return this.toRadix(t);
                i = 2
            }
            var e, r = (1 << i) - 1,
            n = !1,
            s = "",
            o = this.t,
            u = this.DB - o * this.DB % i;
            if (o-->0) for (u < this.DB && (e = this[o] >> u) > 0 && (n = !0, s = h(e)); o >= 0;) u < i ? (e = (this[o] & (1 << u) - 1) << i - u, e |= this[--o] >> (u += this.DB - i)) : (e = this[o] >> (u -= i) & r, u <= 0 && (u += this.DB, --o)),
            e > 0 && (n = !0),
            n && (s += h(e));
            return n ? s: "0"
        }
        function v() {
            var t = r();
            return e.ZERO.subTo(this, t),
            t
        }
        function g() {
            return this.s < 0 ? this.negate() : this
        }
        function m(t) {
            var i = this.s - t.s;
            if (0 != i) return i;
            var e = this.t;
            if (0 != (i = e - t.t)) return this.s < 0 ? -i: i;
            for (; --e >= 0;) if (0 != (i = this[e] - t[e])) return i;
            return 0
        }
        function d(t) {
            var i, e = 1;
            return 0 != (i = t >>> 16) && (t = i, e += 16),
            0 != (i = t >> 8) && (t = i, e += 8),
            0 != (i = t >> 4) && (t = i, e += 4),
            0 != (i = t >> 2) && (t = i, e += 2),
            0 != (i = t >> 1) && (t = i, e += 1),
            e
        }
        function T() {
            return this.t <= 0 ? 0 : this.DB * (this.t - 1) + d(this[this.t - 1] ^ this.s & this.DM)
        }
        function b(t, i) {
            var e;
            for (e = this.t - 1; e >= 0; --e) i[e + t] = this[e];
            for (e = t - 1; e >= 0; --e) i[e] = 0;
            i.t = this.t + t,
            i.s = this.s
        }
        function F(t, i) {
            for (var e = t; e < this.t; ++e) i[e - t] = this[e];
            i.t = Math.max(this.t - t, 0),
            i.s = this.s
        }
        function B(t, i) {
            var e, r = t % this.DB,
            n = this.DB - r,
            s = (1 << n) - 1,
            o = Math.floor(t / this.DB),
            h = this.s << r & this.DM;
            for (e = this.t - 1; e >= 0; --e) i[e + o + 1] = this[e] >> n | h,
            h = (this[e] & s) << r;
            for (e = o - 1; e >= 0; --e) i[e] = 0;
            i[o] = h,
            i.t = this.t + o + 1,
            i.s = this.s,
            i.clamp()
        }
        function x(t, i) {
            i.s = this.s;
            var e = Math.floor(t / this.DB);
            if (e >= this.t) return void(i.t = 0);
            var r = t % this.DB,
            n = this.DB - r,
            s = (1 << r) - 1;
            i[0] = this[e] >> r;
            for (var o = e + 1; o < this.t; ++o) i[o - e - 1] |= (this[o] & s) << n,
            i[o - e] = this[o] >> r;
            r > 0 && (i[this.t - e - 1] |= (this.s & s) << n),
            i.t = this.t - e,
            i.clamp()
        }
        function w(t, i) {
            for (var e = 0,
            r = 0,
            n = Math.min(t.t, this.t); e < n;) r += this[e] - t[e],
            i[e++] = r & this.DM,
            r >>= this.DB;
            if (t.t < this.t) {
                for (r -= t.s; e < this.t;) r += this[e],
                i[e++] = r & this.DM,
                r >>= this.DB;
                r += this.s
            } else {
                for (r += this.s; e < t.t;) r -= t[e],
                i[e++] = r & this.DM,
                r >>= this.DB;
                r -= t.s
            }
            i.s = r < 0 ? -1 : 0,
            r < -1 ? i[e++] = this.DV + r: r > 0 && (i[e++] = r),
            i.t = e,
            i.clamp()
        }
        function S(t, i) {
            var r = this.abs(),
            n = t.abs(),
            s = r.t;
            for (i.t = s + n.t; --s >= 0;) i[s] = 0;
            for (s = 0; s < n.t; ++s) i[s + r.t] = r.am(0, n[s], i, s, 0, r.t);
            i.s = 0,
            i.clamp(),
            this.s != t.s && e.ZERO.subTo(i, i)
        }
        function k(t) {
            for (var i = this.abs(), e = t.t = 2 * i.t; --e >= 0;) t[e] = 0;
            for (e = 0; e < i.t - 1; ++e) {
                var r = i.am(e, i[e], t, 2 * e, 0, 1); (t[e + i.t] += i.am(e + 1, 2 * i[e], t, 2 * e + 1, r, i.t - e - 1)) >= i.DV && (t[e + i.t] -= i.DV, t[e + i.t + 1] = 1)
            }
            t.t > 0 && (t[t.t - 1] += i.am(e, i[e], t, 2 * e, 0, 1)),
            t.s = 0,
            t.clamp()
        }
        function D(t, i, n) {
            var s = t.abs();
            if (! (s.t <= 0)) {
                var o = this.abs();
                if (o.t < s.t) return null != i && i.fromInt(0),
                void(null != n && this.copyTo(n));
                null == n && (n = r());
                var h = r(),
                u = this.s,
                a = t.s,
                f = this.DB - d(s[s.t - 1]);
                f > 0 ? (s.lShiftTo(f, h), o.lShiftTo(f, n)) : (s.copyTo(h), o.copyTo(n));
                var l = h.t,
                c = h[l - 1];
                if (0 != c) {
                    var p = c * (1 << this.F1) + (l > 1 ? h[l - 2] >> this.F2: 0),
                    y = this.FV / p,
                    v = (1 << this.F1) / p,
                    g = 1 << this.F2,
                    m = n.t,
                    T = m - l,
                    b = null == i ? r() : i;
                    for (h.dlShiftTo(T, b), n.compareTo(b) >= 0 && (n[n.t++] = 1, n.subTo(b, n)), e.ONE.dlShiftTo(l, b), b.subTo(h, h); h.t < l;) h[h.t++] = 0;
                    for (; --T >= 0;) {
                        var F = n[--m] == c ? this.DM: Math.floor(n[m] * y + (n[m - 1] + g) * v);
                        if ((n[m] += h.am(0, F, n, T, 0, l)) < F) for (h.dlShiftTo(T, b), n.subTo(b, n); n[m] < --F;) n.subTo(b, n)
                    }
                    null != i && (n.drShiftTo(l, i), u != a && e.ZERO.subTo(i, i)),
                    n.t = l,
                    n.clamp(),
                    f > 0 && n.rShiftTo(f, n),
                    u < 0 && e.ZERO.subTo(n, n)
                }
            }
        }
        function I(t) {
            var i = r();
            return this.abs().divRemTo(t, null, i),
            this.s < 0 && i.compareTo(e.ZERO) > 0 && t.subTo(i, i),
            i
        }
        function E(t) {
            this.m = t
        }
        function O(t) {
            return t.s < 0 || t.compareTo(this.m) >= 0 ? t.mod(this.m) : t
        }
        function q(t) {
            return t
        }
        function A(t) {
            t.divRemTo(this.m, null, t)
        }
        function R(t, i, e) {
            t.multiplyTo(i, e),
            this.reduce(e)
        }
        function V(t, i) {
            t.squareTo(i),
            this.reduce(i)
        }
        function M() {
            if (this.t < 1) return 0;
            var t = this[0];
            if (0 == (1 & t)) return 0;
            var i = 3 & t;
            return i = i * (2 - (15 & t) * i) & 15,
            i = i * (2 - (255 & t) * i) & 255,
            i = i * (2 - ((65535 & t) * i & 65535)) & 65535,
            i = i * (2 - t * i % this.DV) % this.DV,
            i > 0 ? this.DV - i: -i
        }
        function _(t) {
            this.m = t,
            this.mp = t.invDigit(),
            this.mpl = 32767 & this.mp,
            this.mph = this.mp >> 15,
            this.um = (1 << t.DB - 15) - 1,
            this.mt2 = 2 * t.t
        }
        function C(t) {
            var i = r();
            return t.abs().dlShiftTo(this.m.t, i),
            i.divRemTo(this.m, null, i),
            t.s < 0 && i.compareTo(e.ZERO) > 0 && this.m.subTo(i, i),
            i
        }
        function L(t) {
            var i = r();
            return t.copyTo(i),
            this.reduce(i),
            i
        }
        function P(t) {
            for (; t.t <= this.mt2;) t[t.t++] = 0;
            for (var i = 0; i < this.m.t; ++i) {
                var e = 32767 & t[i],
                r = e * this.mpl + ((e * this.mph + (t[i] >> 15) * this.mpl & this.um) << 15) & t.DM;
                for (e = i + this.m.t, t[e] += this.m.am(0, r, t, i, 0, this.m.t); t[e] >= t.DV;) t[e] -= t.DV,
                t[++e]++
            }
            t.clamp(),
            t.drShiftTo(this.m.t, t),
            t.compareTo(this.m) >= 0 && t.subTo(this.m, t)
        }
        function H(t, i) {
            t.squareTo(i),
            this.reduce(i)
        }
        function N(t, i, e) {
            t.multiplyTo(i, e),
            this.reduce(e)
        }
        function U() {
            return 0 == (this.t > 0 ? 1 & this[0] : this.s)
        }
        function z(t, i) {
            if (t > 4294967295 || t < 1) return e.ONE;
            var n = r(),
            s = r(),
            o = i.convert(this),
            h = d(t) - 1;
            for (o.copyTo(n); --h >= 0;) if (i.sqrTo(n, s), (t & 1 << h) > 0) i.mulTo(s, o, n);
            else {
                var u = n;
                n = s,
                s = u
            }
            return i.revert(n)
        }
        function j(t, i) {
            var e;
            return e = t < 256 || i.isEven() ? new E(i) : new _(i),
            this.exp(t, e)
        }
        function X() {
            var t = r();
            return this.copyTo(t),
            t
        }
        function Z() {
            if (this.s < 0) {
                if (1 == this.t) return this[0] - this.DV;
                if (0 == this.t) return - 1
            } else {
                if (1 == this.t) return this[0];
                if (0 == this.t) return 0
            }
            return (this[1] & (1 << 32 - this.DB) - 1) << this.DB | this[0]
        }
        function K() {
            return 0 == this.t ? this.s: this[0] << 24 >> 24
        }
        function G() {
            return 0 == this.t ? this.s: this[0] << 16 >> 16
        }
        function Y(t) {
            return Math.floor(Math.LN2 * this.DB / Math.log(t))
        }
        function W() {
            return this.s < 0 ? -1 : this.t <= 0 || 1 == this.t && this[0] <= 0 ? 0 : 1
        }
        function J(t) {
            if (null == t && (t = 10), 0 == this.signum() || t < 2 || t > 36) return "0";
            var i = this.chunkSize(t),
            e = Math.pow(t, i),
            n = l(e),
            s = r(),
            o = r(),
            h = "";
            for (this.divRemTo(n, s, o); s.signum() > 0;) h = (e + o.intValue()).toString(t).substr(1) + h,
            s.divRemTo(n, s, o);
            return o.intValue().toString(t) + h
        }
        function Q(t, i) {
            this.fromInt(0),
            null == i && (i = 10);
            for (var r = this.chunkSize(i), n = Math.pow(i, r), s = !1, o = 0, h = 0, a = 0; a < t.length; ++a) {
                var f = u(t, a);
                f < 0 ? "-" == t.charAt(a) && 0 == this.signum() && (s = !0) : (h = i * h + f, ++o >= r && (this.dMultiply(n), this.dAddOffset(h, 0), o = 0, h = 0))
            }
            o > 0 && (this.dMultiply(Math.pow(i, o)), this.dAddOffset(h, 0)),
            s && e.ZERO.subTo(this, this)
        }
        function $(t, i, r) {
            if ("number" == typeof i) if (t < 2) this.fromInt(1);
            else for (this.fromNumber(t, r), this.testBit(t - 1) || this.bitwiseTo(e.ONE.shiftLeft(t - 1), ht, this), this.isEven() && this.dAddOffset(1, 0); ! this.isProbablePrime(i);) this.dAddOffset(2, 0),
            this.bitLength() > t && this.subTo(e.ONE.shiftLeft(t - 1), this);
            else {
                var n = new Array,
                s = 7 & t;
                n.length = 1 + (t >> 3),
                i.nextBytes(n),
                s > 0 ? n[0] &= (1 << s) - 1 : n[0] = 0,
                this.fromString(n, 256)
            }
        }
        function tt() {
            var t = this.t,
            i = new Array;
            i[0] = this.s;
            var e, r = this.DB - t * this.DB % 8,
            n = 0;
            if (t-->0) for (r < this.DB && (e = this[t] >> r) != (this.s & this.DM) >> r && (i[n++] = e | this.s << this.DB - r); t >= 0;) r < 8 ? (e = (this[t] & (1 << r) - 1) << 8 - r, e |= this[--t] >> (r += this.DB - 8)) : (e = this[t] >> (r -= 8) & 255, r <= 0 && (r += this.DB, --t)),
            0 != (128 & e) && (e |= -256),
            0 == n && (128 & this.s) != (128 & e) && ++n,
            (n > 0 || e != this.s) && (i[n++] = e);
            return i
        }
        function it(t) {
            return 0 == this.compareTo(t)
        }
        function et(t) {
            return this.compareTo(t) < 0 ? this: t
        }
        function rt(t) {
            return this.compareTo(t) > 0 ? this: t
        }
        function nt(t, i, e) {
            var r, n, s = Math.min(t.t, this.t);
            for (r = 0; r < s; ++r) e[r] = i(this[r], t[r]);
            if (t.t < this.t) {
                for (n = t.s & this.DM, r = s; r < this.t; ++r) e[r] = i(this[r], n);
                e.t = this.t
            } else {
                for (n = this.s & this.DM, r = s; r < t.t; ++r) e[r] = i(n, t[r]);
                e.t = t.t
            }
            e.s = i(this.s, t.s),
            e.clamp()
        }
        function st(t, i) {
            return t & i
        }
        function ot(t) {
            var i = r();
            return this.bitwiseTo(t, st, i),
            i
        }
        function ht(t, i) {
            return t | i
        }
        function ut(t) {
            var i = r();
            return this.bitwiseTo(t, ht, i),
            i
        }
        function at(t, i) {
            return t ^ i
        }
        function ft(t) {
            var i = r();
            return this.bitwiseTo(t, at, i),
            i
        }
        function lt(t, i) {
            return t & ~i
        }
        function ct(t) {
            var i = r();
            return this.bitwiseTo(t, lt, i),
            i
        }
        function pt() {
            for (var t = r(), i = 0; i < this.t; ++i) t[i] = this.DM & ~this[i];
            return t.t = this.t,
            t.s = ~this.s,
            t
        }
        function yt(t) {
            var i = r();
            return t < 0 ? this.rShiftTo( - t, i) : this.lShiftTo(t, i),
            i
        }
        function vt(t) {
            var i = r();
            return t < 0 ? this.lShiftTo( - t, i) : this.rShiftTo(t, i),
            i
        }
        function gt(t) {
            if (0 == t) return - 1;
            var i = 0;
            return 0 == (65535 & t) && (t >>= 16, i += 16),
            0 == (255 & t) && (t >>= 8, i += 8),
            0 == (15 & t) && (t >>= 4, i += 4),
            0 == (3 & t) && (t >>= 2, i += 2),
            0 == (1 & t) && ++i,
            i
        }
        function mt() {
            for (var t = 0; t < this.t; ++t) if (0 != this[t]) return t * this.DB + gt(this[t]);
            return this.s < 0 ? this.t * this.DB: -1
        }
        function dt(t) {
            for (var i = 0; 0 != t;) t &= t - 1,
            ++i;
            return i
        }
        function Tt() {
            for (var t = 0,
            i = this.s & this.DM,
            e = 0; e < this.t; ++e) t += dt(this[e] ^ i);
            return t
        }
        function bt(t) {
            var i = Math.floor(t / this.DB);
            return i >= this.t ? 0 != this.s: 0 != (this[i] & 1 << t % this.DB)
        }
        function Ft(t, i) {
            var r = e.ONE.shiftLeft(t);
            return this.bitwiseTo(r, i, r),
            r
        }
        function Bt(t) {
            return this.changeBit(t, ht)
        }
        function xt(t) {
            return this.changeBit(t, lt)
        }
        function wt(t) {
            return this.changeBit(t, at)
        }
        function St(t, i) {
            for (var e = 0,
            r = 0,
            n = Math.min(t.t, this.t); e < n;) r += this[e] + t[e],
            i[e++] = r & this.DM,
            r >>= this.DB;
            if (t.t < this.t) {
                for (r += t.s; e < this.t;) r += this[e],
                i[e++] = r & this.DM,
                r >>= this.DB;
                r += this.s
            } else {
                for (r += this.s; e < t.t;) r += t[e],
                i[e++] = r & this.DM,
                r >>= this.DB;
                r += t.s
            }
            i.s = r < 0 ? -1 : 0,
            r > 0 ? i[e++] = r: r < -1 && (i[e++] = this.DV + r),
            i.t = e,
            i.clamp()
        }
        function kt(t) {
            var i = r();
            return this.addTo(t, i),
            i
        }
        function Dt(t) {
            var i = r();
            return this.subTo(t, i),
            i
        }
        function It(t) {
            var i = r();
            return this.multiplyTo(t, i),
            i
        }
        function Et() {
            var t = r();
            return this.squareTo(t),
            t
        }
        function Ot(t) {
            var i = r();
            return this.divRemTo(t, i, null),
            i
        }
        function qt(t) {
            var i = r();
            return this.divRemTo(t, null, i),
            i
        }
        function At(t) {
            var i = r(),
            e = r();
            return this.divRemTo(t, i, e),
            new Array(i, e)
        }
        function Rt(t) {
            this[this.t] = this.am(0, t - 1, this, 0, 0, this.t),
            ++this.t,
            this.clamp()
        }
        function Vt(t, i) {
            if (0 != t) {
                for (; this.t <= i;) this[this.t++] = 0;
                for (this[i] += t; this[i] >= this.DV;) this[i] -= this.DV,
                ++i >= this.t && (this[this.t++] = 0),
                ++this[i]
            }
        }
        function Mt() {}
        function _t(t) {
            return t
        }
        function Ct(t, i, e) {
            t.multiplyTo(i, e)
        }
        function Lt(t, i) {
            t.squareTo(i)
        }
        function Pt(t) {
            return this.exp(t, new Mt)
        }
        function Ht(t, i, e) {
            var r = Math.min(this.t + t.t, i);
            for (e.s = 0, e.t = r; r > 0;) e[--r] = 0;
            var n;
            for (n = e.t - this.t; r < n; ++r) e[r + this.t] = this.am(0, t[r], e, r, 0, this.t);
            for (n = Math.min(t.t, i); r < n; ++r) this.am(0, t[r], e, r, 0, i - r);
            e.clamp()
        }
        function Nt(t, i, e) {--i;
            var r = e.t = this.t + t.t - i;
            for (e.s = 0; --r >= 0;) e[r] = 0;
            for (r = Math.max(i - this.t, 0); r < t.t; ++r) e[this.t + r - i] = this.am(i - r, t[r], e, 0, 0, this.t + r - i);
            e.clamp(),
            e.drShiftTo(1, e)
        }
        function Ut(t) {
            this.r2 = r(),
            this.q3 = r(),
            e.ONE.dlShiftTo(2 * t.t, this.r2),
            this.mu = this.r2.divide(t),
            this.m = t
        }
        function zt(t) {
            if (t.s < 0 || t.t > 2 * this.m.t) return t.mod(this.m);
            if (t.compareTo(this.m) < 0) return t;
            var i = r();
            return t.copyTo(i),
            this.reduce(i),
            i
        }
        function jt(t) {
            return t
        }
        function Xt(t) {
            for (t.drShiftTo(this.m.t - 1, this.r2), t.t > this.m.t + 1 && (t.t = this.m.t + 1, t.clamp()), this.mu.multiplyUpperTo(this.r2, this.m.t + 1, this.q3), this.m.multiplyLowerTo(this.q3, this.m.t + 1, this.r2); t.compareTo(this.r2) < 0;) t.dAddOffset(1, this.m.t + 1);
            for (t.subTo(this.r2, t); t.compareTo(this.m) >= 0;) t.subTo(this.m, t)
        }
        function Zt(t, i) {
            t.squareTo(i),
            this.reduce(i)
        }
        function Kt(t, i, e) {
            t.multiplyTo(i, e),
            this.reduce(e)
        }
        function Gt(t, i) {
            var e, n, s = t.bitLength(),
            o = l(1);
            if (s <= 0) return o;
            e = s < 18 ? 1 : s < 48 ? 3 : s < 144 ? 4 : s < 768 ? 5 : 6,
            n = s < 8 ? new E(i) : i.isEven() ? new Ut(i) : new _(i);
            var h = new Array,
            u = 3,
            a = e - 1,
            f = (1 << e) - 1;
            if (h[1] = n.convert(this), e > 1) {
                var c = r();
                for (n.sqrTo(h[1], c); u <= f;) h[u] = r(),
                n.mulTo(c, h[u - 2], h[u]),
                u += 2
            }
            var p, y, v = t.t - 1,
            g = !0,
            m = r();
            for (s = d(t[v]) - 1; v >= 0;) {
                for (s >= a ? p = t[v] >> s - a & f: (p = (t[v] & (1 << s + 1) - 1) << a - s, v > 0 && (p |= t[v - 1] >> this.DB + s - a)), u = e; 0 == (1 & p);) p >>= 1,
                --u;
                if ((s -= u) < 0 && (s += this.DB, --v), g) h[p].copyTo(o),
                g = !1;
                else {
                    for (; u > 1;) n.sqrTo(o, m),
                    n.sqrTo(m, o),
                    u -= 2;
                    u > 0 ? n.sqrTo(o, m) : (y = o, o = m, m = y),
                    n.mulTo(m, h[p], o)
                }
                for (; v >= 0 && 0 == (t[v] & 1 << s);) n.sqrTo(o, m),
                y = o,
                o = m,
                m = y,
                --s < 0 && (s = this.DB - 1, --v)
            }
            return n.revert(o)
        }
        function Yt(t) {
            var i = this.s < 0 ? this.negate() : this.clone(),
            e = t.s < 0 ? t.negate() : t.clone();
            if (i.compareTo(e) < 0) {
                var r = i;
                i = e,
                e = r
            }
            var n = i.getLowestSetBit(),
            s = e.getLowestSetBit();
            if (s < 0) return i;
            for (n < s && (s = n), s > 0 && (i.rShiftTo(s, i), e.rShiftTo(s, e)); i.signum() > 0;)(n = i.getLowestSetBit()) > 0 && i.rShiftTo(n, i),
            (n = e.getLowestSetBit()) > 0 && e.rShiftTo(n, e),
            i.compareTo(e) >= 0 ? (i.subTo(e, i), i.rShiftTo(1, i)) : (e.subTo(i, e), e.rShiftTo(1, e));
            return s > 0 && e.lShiftTo(s, e),
            e
        }
        function Wt(t) {
            if (t <= 0) return 0;
            var i = this.DV % t,
            e = this.s < 0 ? t - 1 : 0;
            if (this.t > 0) if (0 == i) e = this[0] % t;
            else for (var r = this.t - 1; r >= 0; --r) e = (i * e + this[r]) % t;
            return e
        }
        function Jt(t) {
            var i = t.isEven();
            if (this.isEven() && i || 0 == t.signum()) return e.ZERO;
            for (var r = t.clone(), n = this.clone(), s = l(1), o = l(0), h = l(0), u = l(1); 0 != r.signum();) {
                for (; r.isEven();) r.rShiftTo(1, r),
                i ? (s.isEven() && o.isEven() || (s.addTo(this, s), o.subTo(t, o)), s.rShiftTo(1, s)) : o.isEven() || o.subTo(t, o),
                o.rShiftTo(1, o);
                for (; n.isEven();) n.rShiftTo(1, n),
                i ? (h.isEven() && u.isEven() || (h.addTo(this, h), u.subTo(t, u)), h.rShiftTo(1, h)) : u.isEven() || u.subTo(t, u),
                u.rShiftTo(1, u);
                r.compareTo(n) >= 0 ? (r.subTo(n, r), i && s.subTo(h, s), o.subTo(u, o)) : (n.subTo(r, n), i && h.subTo(s, h), u.subTo(o, u))
            }
            return 0 != n.compareTo(e.ONE) ? e.ZERO: u.compareTo(t) >= 0 ? u.subtract(t) : u.signum() < 0 ? (u.addTo(t, u), u.signum() < 0 ? u.add(t) : u) : u
        }
        function Qt(t) {
            var i, e = this.abs();
            if (1 == e.t && e[0] <= vi[vi.length - 1]) {
                for (i = 0; i < vi.length; ++i) if (e[0] == vi[i]) return ! 0;
                return ! 1
            }
            if (e.isEven()) return ! 1;
            for (i = 1; i < vi.length;) {
                for (var r = vi[i], n = i + 1; n < vi.length && r < gi;) r *= vi[n++];
                for (r = e.modInt(r); i < n;) if (r % vi[i++] == 0) return ! 1
            }
            return e.millerRabin(t)
        }
        function $t(t) {
            var i = this.subtract(e.ONE),
            n = i.getLowestSetBit();
            if (n <= 0) return ! 1;
            var s = i.shiftRight(n); (t = t + 1 >> 1) > vi.length && (t = vi.length);
            for (var o = r(), h = 0; h < t; ++h) {
                o.fromInt(vi[Math.floor(Math.random() * vi.length)]);
                var u = o.modPow(s, this);
                if (0 != u.compareTo(e.ONE) && 0 != u.compareTo(i)) {
                    for (var a = 1; a++<n && 0 != u.compareTo(i);) if (u = u.modPowInt(2, this), 0 == u.compareTo(e.ONE)) return ! 1;
                    if (0 != u.compareTo(i)) return ! 1
                }
            }
            return ! 0
        }
        function ti(t) {
            di[Ti++] ^= 255 & t,
            di[Ti++] ^= t >> 8 & 255,
            di[Ti++] ^= t >> 16 & 255,
            di[Ti++] ^= t >> 24 & 255,
            Ti >= xi && (Ti -= xi)
        }
        function ii() {
            ti((new Date).getTime())
        }
        function ei() {
            if (null == mi) {
                for (ii(), mi = ui(), mi.init(di), Ti = 0; Ti < di.length; ++Ti) di[Ti] = 0;
                Ti = 0
            }
            return mi.next()
        }
        function ri(t) {
            var i;
            for (i = 0; i < t.length; ++i) t[i] = ei()
        }
        function ni() {}
        function si() {
            this.i = 0,
            this.j = 0,
            this.S = new Array
        }
        function oi(t) {
            var i, e, r;
            for (i = 0; i < 256; ++i) this.S[i] = i;
            for (e = 0, i = 0; i < 256; ++i) e = e + this.S[i] + t[i % t.length] & 255,
            r = this.S[i],
            this.S[i] = this.S[e],
            this.S[e] = r;
            this.i = 0,
            this.j = 0
        }
        function hi() {
            var t;
            return this.i = this.i + 1 & 255,
            this.j = this.j + this.S[this.i] & 255,
            t = this.S[this.i],
            this.S[this.i] = this.S[this.j],
            this.S[this.j] = t,
            this.S[t + this.S[this.i] & 255]
        }
        function ui() {
            return new si
        }
        var ai, fi = "undefined" != typeof navigator;
        fi && "Microsoft Internet Explorer" == navigator.appName ? (e.prototype.am = s, ai = 30) : fi && "Netscape" != navigator.appName ? (e.prototype.am = n, ai = 26) : (e.prototype.am = o, ai = 28),
        e.prototype.DB = ai,
        e.prototype.DM = (1 << ai) - 1,
        e.prototype.DV = 1 << ai;
        e.prototype.FV = Math.pow(2, 52),
        e.prototype.F1 = 52 - ai,
        e.prototype.F2 = 2 * ai - 52;
        var li, ci, pi = "0123456789abcdefghijklmnopqrstuvwxyz",
        yi = new Array;
        for (li = "0".charCodeAt(0), ci = 0; ci <= 9; ++ci) yi[li++] = ci;
        for (li = "a".charCodeAt(0), ci = 10; ci < 36; ++ci) yi[li++] = ci;
        for (li = "A".charCodeAt(0), ci = 10; ci < 36; ++ci) yi[li++] = ci;
        E.prototype.convert = O,
        E.prototype.revert = q,
        E.prototype.reduce = A,
        E.prototype.mulTo = R,
        E.prototype.sqrTo = V,
        _.prototype.convert = C,
        _.prototype.revert = L,
        _.prototype.reduce = P,
        _.prototype.mulTo = N,
        _.prototype.sqrTo = H,
        e.prototype.copyTo = a,
        e.prototype.fromInt = f,
        e.prototype.fromString = c,
        e.prototype.clamp = p,
        e.prototype.dlShiftTo = b,
        e.prototype.drShiftTo = F,
        e.prototype.lShiftTo = B,
        e.prototype.rShiftTo = x,
        e.prototype.subTo = w,
        e.prototype.multiplyTo = S,
        e.prototype.squareTo = k,
        e.prototype.divRemTo = D,
        e.prototype.invDigit = M,
        e.prototype.isEven = U,
        e.prototype.exp = z,
        e.prototype.toString = y,
        e.prototype.negate = v,
        e.prototype.abs = g,
        e.prototype.compareTo = m,
        e.prototype.bitLength = T,
        e.prototype.mod = I,
        e.prototype.modPowInt = j,
        e.ZERO = l(0),
        e.ONE = l(1),
        Mt.prototype.convert = _t,
        Mt.prototype.revert = _t,
        Mt.prototype.mulTo = Ct,
        Mt.prototype.sqrTo = Lt,
        Ut.prototype.convert = zt,
        Ut.prototype.revert = jt,
        Ut.prototype.reduce = Xt,
        Ut.prototype.mulTo = Kt,
        Ut.prototype.sqrTo = Zt;
        var vi = [2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691, 701, 709, 719, 727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797, 809, 811, 821, 823, 827, 829, 839, 853, 857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937, 941, 947, 953, 967, 971, 977, 983, 991, 997],
        gi = (1 << 26) / vi[vi.length - 1];
        e.prototype.chunkSize = Y,
        e.prototype.toRadix = J,
        e.prototype.fromRadix = Q,
        e.prototype.fromNumber = $,
        e.prototype.bitwiseTo = nt,
        e.prototype.changeBit = Ft,
        e.prototype.addTo = St,
        e.prototype.dMultiply = Rt,
        e.prototype.dAddOffset = Vt,
        e.prototype.multiplyLowerTo = Ht,
        e.prototype.multiplyUpperTo = Nt,
        e.prototype.modInt = Wt,
        e.prototype.millerRabin = $t,
        e.prototype.clone = X,
        e.prototype.intValue = Z,
        e.prototype.byteValue = K,
        e.prototype.shortValue = G,
        e.prototype.signum = W,
        e.prototype.toByteArray = tt,
        e.prototype.equals = it,
        e.prototype.min = et,
        e.prototype.max = rt,
        e.prototype.and = ot,
        e.prototype.or = ut,
        e.prototype.xor = ft,
        e.prototype.andNot = ct,
        e.prototype.not = pt,
        e.prototype.shiftLeft = yt,
        e.prototype.shiftRight = vt,
        e.prototype.getLowestSetBit = mt,
        e.prototype.bitCount = Tt,
        e.prototype.testBit = bt,
        e.prototype.setBit = Bt,
        e.prototype.clearBit = xt,
        e.prototype.flipBit = wt,
        e.prototype.add = kt,
        e.prototype.subtract = Dt,
        e.prototype.multiply = It,
        e.prototype.divide = Ot,
        e.prototype.remainder = qt,
        e.prototype.divideAndRemainder = At,
        e.prototype.modPow = Gt,
        e.prototype.modInverse = Jt,
        e.prototype.pow = Pt,
        e.prototype.gcd = Yt,
        e.prototype.isProbablePrime = Qt,
        e.prototype.square = Et,
        e.prototype.Barrett = Ut;
        var mi, di, Ti;
        if (null == di) {
            di = new Array,
            Ti = 0;
            var bi;
            if ("undefined" != typeof window && window.crypto) if (window.crypto.getRandomValues) {
                var Fi = new Uint8Array(32);
                for (window.crypto.getRandomValues(Fi), bi = 0; bi < 32; ++bi) di[Ti++] = Fi[bi]
            } else if ("Netscape" == navigator.appName && navigator.appVersion < "5") {
                var Bi = window.crypto.random(32);
                for (bi = 0; bi < Bi.length; ++bi) di[Ti++] = 255 & Bi.charCodeAt(bi)
            }
            for (; Ti < xi;) bi = Math.floor(65536 * Math.random()),
            di[Ti++] = bi >>> 8,
            di[Ti++] = 255 & bi;
            Ti = 0,
            ii()
        }
        ni.prototype.nextBytes = ri,
        si.prototype.init = oi,
        si.prototype.next = hi;
        var xi = 256;
        i = t.exports = {
        default:
            e,
            BigInteger: e,
            SecureRandom: ni
        }
    }).call(this)
},
function(t, i, e) {
    "use strict";
    function r() {
        return T
    }
    function n() {
        var t = new p("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFF", 16),
        i = new p("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFC", 16),
        e = new p("28E9FA9E9D9F5E344D5A9E4BCF6509A7F39789F515AB8F92DDBCBD414D940E93", 16),
        r = new g(t, i, e);
        return {
            curve: r,
            G: r.decodePointHex("0432C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7BC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0"),
            n: new p("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFF7203DF6B21C6052B53BBF40939D54123", 16)
        }
    }
    function s() {
        var t = new p(F.bitLength(), m).mod(F.subtract(p.ONE)).add(p.ONE),
        i = u(t.toString(16), 64),
        e = b.multiply(t);
        return {
            privateKey: i,
            publicKey: "04" + u(e.getX().toBigInteger().toString(16), 64) + u(e.getY().toBigInteger().toString(16), 64)
        }
    }
    function o(t) {
        t = unescape(encodeURIComponent(t));
        for (var i = t.length,
        e = [], r = 0; r < i; r++) e[r >>> 2] |= (255 & t.charCodeAt(r)) << 24 - r % 4 * 8;
        for (var n = [], s = 0; s < i; s++) {
            var o = e[s >>> 2] >>> 24 - s % 4 * 8 & 255;
            n.push((o >>> 4).toString(16)),
            n.push((15 & o).toString(16))
        }
        return n.join("")
    }
    function h(t) {
        return Array.prototype.map.call(new Uint8Array(t),
        function(t) {
            return ("00" + t.toString(16)).slice( - 2)
        }).join("")
    }
    function u(t, i) {
        return t.length >= i ? t: new Array(i - t.length + 1).join("0") + t
    }
    function a(t) {
        for (var i = [], e = 0, r = 0; r < 2 * t.length; r += 2) i[r >>> 3] |= parseInt(t[e], 10) << 24 - r % 8 * 4,
        e++;
        for (var n = [], s = 0; s < t.length; s++) {
            var o = i[s >>> 2] >>> 24 - s % 4 * 8 & 255;
            n.push((o >>> 4).toString(16)),
            n.push((15 & o).toString(16))
        }
        return n.join("")
    }
    function f(t) {
        for (var i = [], e = 0, r = 0; r < 2 * t.length; r += 2) i[r >>> 3] |= parseInt(t[e], 10) << 24 - r % 8 * 4,
        e++;
        try {
            for (var n = [], s = 0; s < t.length; s++) {
                var o = i[s >>> 2] >>> 24 - s % 4 * 8 & 255;
                n.push(String.fromCharCode(o))
            }
            return decodeURIComponent(escape(n.join("")))
        } catch(t) {
            throw new Error("Malformed UTF-8 data")
        }
    }
    function l(t) {
        var i = [],
        e = t.length;
        e % 2 != 0 && (t = u(t, e + 1)),
        e = t.length;
        for (var r = 0; r < e; r += 2) i.push(parseInt(t.substr(r, 2), 16));
        return i
    }
    var c = e(0),
    p = c.BigInteger,
    y = c.SecureRandom,
    v = e(5),
    g = v.ECCurveFp,
    m = new y,
    d = n(),
    T = d.curve,
    b = d.G,
    F = d.n;
    t.exports = {
        getGlobalCurve: r,
        generateEcparam: n,
        generateKeyPairHex: s,
        parseUtf8StringToHex: o,
        parseArrayBufferToHex: h,
        leftPad: u,
        arrayToHex: a,
        arrayToUtf8: f,
        hexToArray: l
    }
},
function(t, i, e) {
    "use strict";
    function r(t, i) {
        if (! (t instanceof i)) throw new TypeError("Cannot call a class as a function")
    }
    var n = function() {
        function t(t, i) {
            for (var e = 0; e < i.length; e++) {
                var r = i[e];
                r.enumerable = r.enumerable || !1,
                r.configurable = !0,
                "value" in r && (r.writable = !0),
                Object.defineProperty(t, r.key, r)
            }
        }
        return function(i, e, r) {
            return e && t(i.prototype, e),
            r && t(i, r),
            i
        }
    } (),
    s = e(0),
    o = s.BigInteger,
    h = e(1),
    u = function(t, i, e, r, n) {
        for (var s = 0; s < n; s++) e[r + s] = t[i + s]
    },
    a = {
        minValue: -parseInt("10000000000000000000000000000000", 2),
        maxValue: parseInt("1111111111111111111111111111111", 2),
        parse: function(t) {
            if (t < this.minValue) {
                for (var i = new Number( - t), e = i.toString(2), r = e.substr(e.length - 31, 31), n = "", s = 0; s < r.length; s++) {
                    n += "0" == r.substr(s, 1) ? "1": "0"
                }
                return parseInt(n, 2) + 1
            }
            if (t > this.maxValue) {
                for (var o = Number(t), h = o.toString(2), u = h.substr(h.length - 31, 31), a = "", f = 0; f < u.length; f++) {
                    a += "0" == u.substr(f, 1) ? "1": "0"
                }
                return - (parseInt(a, 2) + 1)
            }
            return t
        },
        parseByte: function(t) {
            if (t < 0) {
                for (var i = new Number( - t), e = i.toString(2), r = e.substr(e.length - 8, 8), n = "", s = 0; s < r.length; s++) {
                    n += "0" == r.substr(s, 1) ? "1": "0"
                }
                return parseInt(n, 2) + 1
            }
            if (t > 255) {
                var o = Number(t),
                h = o.toString(2);
                return parseInt(h.substr(h.length - 8, 8), 2)
            }
            return t
        }
    },
    f = function() {
        function t() {
            r(this, t),
            this.xBuf = new Array,
            this.xBufOff = 0,
            this.byteCount = 0,
            this.DIGEST_LENGTH = 32,
            this.v0 = [1937774191, 1226093241, 388252375, 3666478592, 2842636476, 372324522, 3817729613, 2969243214],
            this.v0 = [1937774191, 1226093241, 388252375, -628488704, -1452330820, 372324522, -477237683, -1325724082],
            this.v = new Array(8),
            this.v_ = new Array(8),
            this.X0 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
            this.X = new Array(68),
            this.xOff = 0,
            this.T_00_15 = 2043430169,
            this.T_16_63 = 2055708042,
            arguments.length > 0 ? this.initDigest(arguments[0]) : this.init()
        }
        return n(t, [{
            key: "init",
            value: function() {
                this.xBuf = new Array(4),
                this.reset()
            }
        },
        {
            key: "initDigest",
            value: function(t) {
                this.xBuf = [].concat(t.xBuf),
                this.xBufOff = t.xBufOff,
                this.byteCount = t.byteCount,
                u(t.X, 0, this.X, 0, t.X.length),
                this.xOff = t.xOff,
                u(t.v, 0, this.v, 0, t.v.length)
            }
        },
        {
            key: "getDigestSize",
            value: function() {
                return this.DIGEST_LENGTH
            }
        },
        {
            key: "reset",
            value: function() {
                this.byteCount = 0,
                this.xBufOff = 0;
                for (var t in this.xBuf) this.xBuf[t] = null;
                u(this.v0, 0, this.v, 0, this.v0.length),
                this.xOff = 0,
                u(this.X0, 0, this.X, 0, this.X0.length)
            }
        },
        {
            key: "processBlock",
            value: function() {
                var t = void 0,
                i = this.X,
                e = new Array(64);
                for (t = 16; t < 68; t++) i[t] = this.p1(i[t - 16] ^ i[t - 9] ^ this.rotate(i[t - 3], 15)) ^ this.rotate(i[t - 13], 7) ^ i[t - 6];
                for (t = 0; t < 64; t++) e[t] = i[t] ^ i[t + 4];
                var r = this.v,
                n = this.v_;
                u(r, 0, n, 0, this.v0.length);
                var s = void 0,
                o = void 0,
                h = void 0,
                f = void 0,
                l = void 0;
                for (t = 0; t < 16; t++) l = this.rotate(n[0], 12),
                s = a.parse(a.parse(l + n[4]) + this.rotate(this.T_00_15, t)),
                s = this.rotate(s, 7),
                o = s ^ l,
                h = a.parse(a.parse(this.ff_00_15(n[0], n[1], n[2]) + n[3]) + o) + e[t],
                f = a.parse(a.parse(this.gg_00_15(n[4], n[5], n[6]) + n[7]) + s) + i[t],
                n[3] = n[2],
                n[2] = this.rotate(n[1], 9),
                n[1] = n[0],
                n[0] = h,
                n[7] = n[6],
                n[6] = this.rotate(n[5], 19),
                n[5] = n[4],
                n[4] = this.p0(f);
                for (t = 16; t < 64; t++) l = this.rotate(n[0], 12),
                s = a.parse(a.parse(l + n[4]) + this.rotate(this.T_16_63, t)),
                s = this.rotate(s, 7),
                o = s ^ l,
                h = a.parse(a.parse(this.ff_16_63(n[0], n[1], n[2]) + n[3]) + o) + e[t],
                f = a.parse(a.parse(this.gg_16_63(n[4], n[5], n[6]) + n[7]) + s) + i[t],
                n[3] = n[2],
                n[2] = this.rotate(n[1], 9),
                n[1] = n[0],
                n[0] = h,
                n[7] = n[6],
                n[6] = this.rotate(n[5], 19),
                n[5] = n[4],
                n[4] = this.p0(f);
                for (t = 0; t < 8; t++) r[t] ^= a.parse(n[t]);
                this.xOff = 0,
                u(this.X0, 0, this.X, 0, this.X0.length)
            }
        },
        {
            key: "processWord",
            value: function(t, i) {
                var e = t[i] << 24;
                e |= (255 & t[++i]) << 16,
                e |= (255 & t[++i]) << 8,
                e |= 255 & t[++i],
                this.X[this.xOff] = e,
                16 == ++this.xOff && this.processBlock()
            }
        },
        {
            key: "processLength",
            value: function(t) {
                this.xOff > 14 && this.processBlock(),
                this.X[14] = this.urShiftLong(t, 32),
                this.X[15] = 4294967295 & t
            }
        },
        {
            key: "intToBigEndian",
            value: function(t, i, e) {
                i[e] = a.parseByte(this.urShift(t, 24)),
                i[++e] = a.parseByte(this.urShift(t, 16)),
                i[++e] = a.parseByte(this.urShift(t, 8)),
                i[++e] = a.parseByte(t)
            }
        },
        {
            key: "doFinal",
            value: function(t, i) {
                this.finish();
                for (var e = 0; e < 8; e++) this.intToBigEndian(this.v[e], t, i + 4 * e);
                return this.reset(),
                this.DIGEST_LENGTH
            }
        },
        {
            key: "update",
            value: function(t) {
                this.xBuf[this.xBufOff++] = t,
                this.xBufOff == this.xBuf.length && (this.processWord(this.xBuf, 0), this.xBufOff = 0),
                this.byteCount++
            }
        },
        {
            key: "blockUpdate",
            value: function(t, i, e) {
                for (; 0 != this.xBufOff && e > 0;) this.update(t[i]),
                i++,
                e--;
                for (; e > this.xBuf.length;) this.processWord(t, i),
                i += this.xBuf.length,
                e -= this.xBuf.length,
                this.byteCount += this.xBuf.length;
                for (; e > 0;) this.update(t[i]),
                i++,
                e--
            }
        },
        {
            key: "finish",
            value: function() {
                var t = this.byteCount << 3;
                for (this.update(128); 0 != this.xBufOff;) this.update(0);
                this.processLength(t),
                this.processBlock()
            }
        },
        {
            key: "rotate",
            value: function(t, i) {
                return t << i | this.urShift(t, 32 - i)
            }
        },
        {
            key: "p0",
            value: function(t) {
                return t ^ this.rotate(t, 9) ^ this.rotate(t, 17)
            }
        },
        {
            key: "p1",
            value: function(t) {
                return t ^ this.rotate(t, 15) ^ this.rotate(t, 23)
            }
        },
        {
            key: "ff_00_15",
            value: function(t, i, e) {
                return t ^ i ^ e
            }
        },
        {
            key: "ff_16_63",
            value: function(t, i, e) {
                return t & i | t & e | i & e
            }
        },
        {
            key: "gg_00_15",
            value: function(t, i, e) {
                return t ^ i ^ e
            }
        },
        {
            key: "gg_16_63",
            value: function(t, i, e) {
                return t & i | ~t & e
            }
        },
        {
            key: "urShift",
            value: function(t, i) {
                return (t > a.maxValue || t < a.minValue) && (t = a.parse(t)),
                t >= 0 ? t >> i: (t >> i) + (2 << ~i)
            }
        },
        {
            key: "urShiftLong",
            value: function(t, i) {
                var e = void 0,
                r = new o;
                if (r.fromInt(t), r.signum() >= 0) e = r.shiftRight(i).intValue();
                else {
                    var n = new o;
                    n.fromInt(2);
                    var s = ~i,
                    h = "";
                    if (s < 0) {
                        for (var u = 64 + s,
                        a = 0; a < u; a++) h += "0";
                        var f = new o;
                        f.fromInt(t >> i);
                        var l = new o("10" + h, 2);
                        h = l.toRadix(10);
                        e = l.add(f).toRadix(10)
                    } else h = n.shiftLeft(~i).intValue(),
                    e = (t >> i) + h
                }
                return e
            }
        },
        {
            key: "getZ",
            value: function(t, i) {
                var e = h.parseUtf8StringToHex("1234567812345678"),
                r = 4 * e.length;
                this.update(r >> 8 & 255),
                this.update(255 & r);
                var n = h.hexToArray(e);
                this.blockUpdate(n, 0, n.length);
                var s = h.hexToArray(t.curve.a.toBigInteger().toRadix(16)),
                o = h.hexToArray(t.curve.b.toBigInteger().toRadix(16)),
                u = h.hexToArray(t.getX().toBigInteger().toRadix(16)),
                a = h.hexToArray(t.getY().toBigInteger().toRadix(16)),
                f = h.hexToArray(i.substr(0, 64)),
                l = h.hexToArray(i.substr(64, 64));
                this.blockUpdate(s, 0, s.length),
                this.blockUpdate(o, 0, o.length),
                this.blockUpdate(u, 0, u.length),
                this.blockUpdate(a, 0, a.length),
                this.blockUpdate(f, 0, f.length),
                this.blockUpdate(l, 0, l.length);
                var c = new Array(this.getDigestSize());
                return this.doFinal(c, 0),
                c
            }
        }]),
        t
    } ();
    t.exports = f
},
function(t, i, e) {
    "use strict";
    function r(t, i) {
        var e = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : 1,
        r = new g;
        t = m.hexToArray(m.parseUtf8StringToHex(t)),
        i.length > 128 && (i = i.substr(i.length - 128));
        var n = i.substr(0, 64),
        s = i.substr(64);
        i = r.createPoint(n, s);
        var o = r.initEncipher(i);
        r.encryptBlock(t);
        var h = m.arrayToHex(t),
        u = new Array(32);
        return r.doFinal(u),
        u = m.arrayToHex(u),
        e === B ? o + h + u: o + u + h
    }
    function n(t, i) {
        var e = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : 1,
        r = new g;
        i = new l(i, 16);
        var n = t.substr(0, 64),
        s = t.substr(0 + n.length, 64),
        o = n.length + s.length,
        h = t.substr(o, 64),
        u = t.substr(o + 64);
        e === B && (h = t.substr(t.length - 64), u = t.substr(o, t.length - o - 64));
        var a = m.hexToArray(u),
        f = r.createPoint(n, s);
        r.initDecipher(i, f),
        r.decryptBlock(a);
        var c = new Array(32);
        if (r.doFinal(c), m.arrayToHex(c) === h) return m.arrayToUtf8(a);
        return ""
    }
    function s(t, i) {
        var e = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : {},
        r = e.pointPool,
        n = e.der,
        s = e.hash,
        o = e.publicKey,
        f = "string" == typeof t ? m.parseUtf8StringToHex(t) : m.parseArrayBufferToHex(t);
        s && (o = o || u(i), f = h(f, o));
        var c = new l(i, 16),
        y = new l(f, 16),
        v = null,
        g = null,
        d = null;
        do {
            do {
                var T = void 0;
                T = r && r.length ? r.pop() : a(), v = T.k, g = y.add(T.x1).mod(F)
            } while ( g . equals ( l . ZERO ) || g.add(v).equals(F));
            d = c.add(l.ONE).modInverse(F).multiply(v.subtract(g.multiply(c))).mod(F)
        } while ( d . equals ( l . ZERO ));
        return n ? p(g, d) : m.leftPad(g.toString(16), 64) + m.leftPad(d.toString(16), 64)
    }
    function o(t, i, e) {
        var r = arguments.length > 3 && void 0 !== arguments[3] ? arguments[3] : {},
        n = r.der,
        s = r.hash,
        o = "string" == typeof t ? m.parseUtf8StringToHex(t) : m.parseArrayBufferToHex(t);
        s && (o = h(o, e));
        var u = void 0,
        a = void 0;
        if (n) {
            var f = y(i);
            u = f.r,
            a = f.s
        } else u = new l(i.substring(0, 64), 16),
        a = new l(i.substring(64), 16);
        var c = b.decodePointHex(e),
        p = new l(o, 16),
        v = u.add(a).mod(F);
        if (v.equals(l.ZERO)) return ! 1;
        var g = T.multiply(a).add(c.multiply(v)),
        d = p.add(g.getX().toBigInteger()).mod(F);
        return u.equals(d)
    }
    function h(t, i) {
        var e = new v,
        r = (new v).getZ(T, i.substr(2, 128)),
        n = m.hexToArray(m.arrayToHex(r).toString()),
        s = t,
        o = m.hexToArray(s),
        h = new Array(e.getDigestSize());
        return e.blockUpdate(n, 0, n.length),
        e.blockUpdate(o, 0, o.length),
        e.doFinal(h, 0),
        m.arrayToHex(h).toString()
    }
    function u(t) {
        var i = T.multiply(new l(t, 16));
        return "04" + m.leftPad(i.getX().toBigInteger().toString(16), 64) + m.leftPad(i.getY().toBigInteger().toString(16), 64)
    }
    function a() {
        var t = m.generateKeyPairHex(),
        i = b.decodePointHex(t.publicKey);
        return t.k = new l(t.privateKey, 16),
        t.x1 = i.getX().toBigInteger(),
        t
    }
    var f = e(0),
    l = f.BigInteger,
    c = e(4),
    p = c.encodeDer,
    y = c.decodeDer,
    v = e(2),
    g = e(6),
    m = e(1),
    d = m.generateEcparam(),
    T = d.G,
    b = d.curve,
    F = d.n,
    B = 0;
    t.exports = {
        generateKeyPairHex: m.generateKeyPairHex,
        doEncrypt: r,
        doDecrypt: n,
        doSignature: s,
        doVerifySignature: o,
        getPoint: a
    }
},
function(t, i, e) {
    "use strict";
    function r(t, i) {
        if (!t) throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
        return ! i || "object" != typeof i && "function" != typeof i ? t: i
    }
    function n(t, i) {
        if ("function" != typeof i && null !== i) throw new TypeError("Super expression must either be null or a function, not " + typeof i);
        t.prototype = Object.create(i && i.prototype, {
            constructor: {
                value: t,
                enumerable: !1,
                writable: !0,
                configurable: !0
            }
        }),
        i && (Object.setPrototypeOf ? Object.setPrototypeOf(t, i) : t.__proto__ = i)
    }
    function s(t, i) {
        if (! (t instanceof i)) throw new TypeError("Cannot call a class as a function")
    }
    function o(t) {
        var i = t.toString(16);
        if ("-" !== i.substr(0, 1)) i.length % 2 == 1 ? i = "0" + i: i.match(/^[0-7]/) || (i = "00" + i);
        else {
            var e = i.substr(1),
            r = e.length;
            r % 2 == 1 ? r += 1 : i.match(/^[0-7]/) || (r += 2);
            for (var n = "",
            s = 0; s < r; s++) n += "f";
            i = new g(n, 16).xor(t).add(g.ONE).toString(16).replace(/^-/, "")
        }
        return i
    }
    function h(t, i) {
        if ("8" !== t.substring(i + 2, i + 3)) return 1;
        var e = parseInt(t.substring(i + 3, i + 4));
        return 0 === e ? -1 : 0 < e && e < 10 ? e + 1 : -2
    }
    function u(t, i) {
        var e = h(t, i);
        return e < 1 ? "": t.substring(i + 2, i + 2 + 2 * e)
    }
    function a(t, i) {
        var e = u(t, i);
        if ("" === e) return - 1;
        var r = void 0;
        return r = parseInt(e.substring(0, 1)) < 8 ? new g(e, 16) : new g(e.substring(2), 16),
        r.intValue()
    }
    function f(t, i) {
        var e = h(t, i);
        return e < 0 ? l_len: i + 2 * (e + 1)
    }
    function l(t, i) {
        var e = f(t, i),
        r = a(t, i);
        return t.substring(e, e + 2 * r)
    }
    function c(t, i) {
        return f(t, i) + 2 * a(t, i)
    }
    function p(t, i) {
        var e = [],
        r = f(t, i);
        e.push(r);
        for (var n = a(t, i), s = r, o = 0;;) {
            var h = c(t, s);
            if (null === h || h - r >= 2 * n) break;
            if (o >= 200) break;
            e.push(h),
            s = h,
            o++
        }
        return e
    }
    var y = function() {
        function t(t, i) {
            for (var e = 0; e < i.length; e++) {
                var r = i[e];
                r.enumerable = r.enumerable || !1,
                r.configurable = !0,
                "value" in r && (r.writable = !0),
                Object.defineProperty(t, r.key, r)
            }
        }
        return function(i, e, r) {
            return e && t(i.prototype, e),
            r && t(i, r),
            i
        }
    } (),
    v = e(0),
    g = v.BigInteger,
    m = function() {
        function t() {
            s(this, t),
            this.isModified = !0,
            this.hTLV = null,
            this.hT = "00",
            this.hL = "00",
            this.hV = ""
        }
        return y(t, [{
            key: "getLengthHexFromValue",
            value: function() {
                var t = this.hV.length / 2,
                i = t.toString(16);
                return i.length % 2 == 1 && (i = "0" + i),
                t < 128 ? i: (128 + i.length / 2).toString(16) + i
            }
        },
        {
            key: "getEncodedHex",
            value: function() {
                return (null == this.hTLV || this.isModified) && (this.hV = this.getFreshValueHex(), this.hL = this.getLengthHexFromValue(), this.hTLV = this.hT + this.hL + this.hV, this.isModified = !1),
                this.hTLV
            }
        },
        {
            key: "getFreshValueHex",
            value: function() {
                return ""
            }
        }]),
        t
    } (),
    d = function(t) {
        function i(t) {
            s(this, i);
            var e = r(this, (i.__proto__ || Object.getPrototypeOf(i)).call(this));
            return e.hT = "02",
            t && t.bigint && (e.hTLV = null, e.isModified = !0, e.hV = o(t.bigint)),
            e
        }
        return n(i, t),
        y(i, [{
            key: "getFreshValueHex",
            value: function() {
                return this.hV
            }
        }]),
        i
    } (m),
    T = function(t) {
        function i(t) {
            s(this, i);
            var e = r(this, (i.__proto__ || Object.getPrototypeOf(i)).call(this));
            return e.hT = "30",
            e.asn1Array = [],
            t && t.array && (e.asn1Array = t.array),
            e
        }
        return n(i, t),
        y(i, [{
            key: "getFreshValueHex",
            value: function() {
                for (var t = "",
                i = 0; i < this.asn1Array.length; i++) {
                    t += this.asn1Array[i].getEncodedHex()
                }
                return this.hV = t,
                this.hV
            }
        }]),
        i
    } (m);
    t.exports = {
        encodeDer: function(t, i) {
            var e = new d({
                bigint: t
            }),
            r = new d({
                bigint: i
            });
            return new T({
                array: [e, r]
            }).getEncodedHex()
        },
        decodeDer: function(t) {
            var i = p(t, 0),
            e = i[0],
            r = i[1],
            n = l(t, e),
            s = l(t, r);
            return {
                r: new g(n, 16),
                s: new g(s, 16)
            }
        }
    }
},
function(t, i, e) {
    "use strict";
    function r(t, i) {
        if (! (t instanceof i)) throw new TypeError("Cannot call a class as a function")
    }
    var n = function() {
        function t(t, i) {
            for (var e = 0; e < i.length; e++) {
                var r = i[e];
                r.enumerable = r.enumerable || !1,
                r.configurable = !0,
                "value" in r && (r.writable = !0),
                Object.defineProperty(t, r.key, r)
            }
        }
        return function(i, e, r) {
            return e && t(i.prototype, e),
            r && t(i, r),
            i
        }
    } (),
    s = e(0),
    o = s.BigInteger,
    h = new o("3"),
    u = function() {
        function t(i, e) {
            r(this, t),
            this.x = e,
            this.q = i
        }
        return n(t, [{
            key: "equals",
            value: function(t) {
                return t === this || this.q.equals(t.q) && this.x.equals(t.x)
            }
        },
        {
            key: "toBigInteger",
            value: function() {
                return this.x
            }
        },
        {
            key: "negate",
            value: function() {
                return new t(this.q, this.x.negate().mod(this.q))
            }
        },
        {
            key: "add",
            value: function(i) {
                return new t(this.q, this.x.add(i.toBigInteger()).mod(this.q))
            }
        },
        {
            key: "subtract",
            value: function(i) {
                return new t(this.q, this.x.subtract(i.toBigInteger()).mod(this.q))
            }
        },
        {
            key: "multiply",
            value: function(i) {
                return new t(this.q, this.x.multiply(i.toBigInteger()).mod(this.q))
            }
        },
        {
            key: "divide",
            value: function(i) {
                return new t(this.q, this.x.multiply(i.toBigInteger().modInverse(this.q)).mod(this.q))
            }
        },
        {
            key: "square",
            value: function() {
                return new t(this.q, this.x.square().mod(this.q))
            }
        }]),
        t
    } (),
    a = function() {
        function t(i, e, n, s) {
            r(this, t),
            this.curve = i,
            this.x = e,
            this.y = n,
            this.z = void 0 === s ? o.ONE: s,
            this.zinv = null
        }
        return n(t, [{
            key: "getX",
            value: function() {
                return null === this.zinv && (this.zinv = this.z.modInverse(this.curve.q)),
                this.curve.fromBigInteger(this.x.toBigInteger().multiply(this.zinv).mod(this.curve.q))
            }
        },
        {
            key: "getY",
            value: function() {
                return null === this.zinv && (this.zinv = this.z.modInverse(this.curve.q)),
                this.curve.fromBigInteger(this.y.toBigInteger().multiply(this.zinv).mod(this.curve.q))
            }
        },
        {
            key: "equals",
            value: function(t) {
                return t === this || (this.isInfinity() ? t.isInfinity() : t.isInfinity() ? this.isInfinity() : !!t.y.toBigInteger().multiply(this.z).subtract(this.y.toBigInteger().multiply(t.z)).mod(this.curve.q).equals(o.ZERO) && t.x.toBigInteger().multiply(this.z).subtract(this.x.toBigInteger().multiply(t.z)).mod(this.curve.q).equals(o.ZERO))
            }
        },
        {
            key: "isInfinity",
            value: function() {
                return null === this.x && null === this.y || this.z.equals(o.ZERO) && !this.y.toBigInteger().equals(o.ZERO)
            }
        },
        {
            key: "negate",
            value: function() {
                return new t(this.curve, this.x, this.y.negate(), this.z)
            }
        },
        {
            key: "add",
            value: function(i) {
                if (this.isInfinity()) return i;
                if (i.isInfinity()) return this;
                var e = this.x.toBigInteger(),
                r = this.y.toBigInteger(),
                n = this.z,
                s = i.x.toBigInteger(),
                h = i.y.toBigInteger(),
                u = i.z,
                a = this.curve.q,
                f = e.multiply(u).mod(a),
                l = s.multiply(n).mod(a),
                c = f.subtract(l),
                p = r.multiply(u).mod(a),
                y = h.multiply(n).mod(a),
                v = p.subtract(y);
                if (o.ZERO.equals(c)) return o.ZERO.equals(v) ? this.twice() : this.curve.infinity;
                var g = f.add(l),
                m = n.multiply(u).mod(a),
                d = c.square().mod(a),
                T = c.multiply(d).mod(a),
                b = m.multiply(v.square()).subtract(g.multiply(d)).mod(a),
                F = c.multiply(b).mod(a),
                B = v.multiply(d.multiply(f).subtract(b)).subtract(p.multiply(T)).mod(a),
                x = T.multiply(m).mod(a);
                return new t(this.curve, this.curve.fromBigInteger(F), this.curve.fromBigInteger(B), x)
            }
        },
        {
            key: "twice",
            value: function() {
                if (this.isInfinity()) return this;
                if (!this.y.toBigInteger().signum()) return this.curve.infinity;
                var i = this.x.toBigInteger(),
                e = this.y.toBigInteger(),
                r = this.z,
                n = this.curve.q,
                s = this.curve.a.toBigInteger(),
                o = i.square().multiply(h).add(s.multiply(r.square())).mod(n),
                u = e.shiftLeft(1).multiply(r).mod(n),
                a = e.square().mod(n),
                f = a.multiply(i).multiply(r).mod(n),
                l = u.square().mod(n),
                c = o.square().subtract(f.shiftLeft(3)).mod(n),
                p = u.multiply(c).mod(n),
                y = o.multiply(f.shiftLeft(2).subtract(c)).subtract(l.shiftLeft(1).multiply(a)).mod(n),
                v = u.multiply(l).mod(n);
                return new t(this.curve, this.curve.fromBigInteger(p), this.curve.fromBigInteger(y), v)
            }
        },
        {
            key: "multiply",
            value: function(t) {
                if (this.isInfinity()) return this;
                if (!t.signum()) return this.curve.infinity;
                for (var i = t.multiply(h), e = this.negate(), r = this, n = i.bitLength() - 2; n > 0; n--) {
                    r = r.twice();
                    var s = i.testBit(n);
                    s !== t.testBit(n) && (r = r.add(s ? this: e))
                }
                return r
            }
        }]),
        t
    } (),
    f = function() {
        function t(i, e, n) {
            r(this, t),
            this.q = i,
            this.a = this.fromBigInteger(e),
            this.b = this.fromBigInteger(n),
            this.infinity = new a(this, null, null)
        }
        return n(t, [{
            key: "equals",
            value: function(t) {
                return t === this || this.q.equals(t.q) && this.a.equals(t.a) && this.b.equals(t.b)
            }
        },
        {
            key: "fromBigInteger",
            value: function(t) {
                return new u(this.q, t)
            }
        },
        {
            key: "decodePointHex",
            value: function(t) {
                switch (parseInt(t.substr(0, 2), 16)) {
                case 0:
                    return this.infinity;
                case 2:
                case 3:
                    return null;
                case 4:
                case 6:
                case 7:
                    var i = (t.length - 2) / 2,
                    e = t.substr(2, i),
                    r = t.substr(i + 2, i);
                    return new a(this, this.fromBigInteger(new o(e, 16)), this.fromBigInteger(new o(r, 16)));
                default:
                    return null
                }
            }
        }]),
        t
    } ();
    t.exports = {
        ECPointFp: a,
        ECCurveFp: f
    }
},
function(t, i, e) {
    "use strict";
    function r(t, i) {
        if (! (t instanceof i)) throw new TypeError("Cannot call a class as a function")
    }
    var n = function() {
        function t(t, i) {
            for (var e = 0; e < i.length; e++) {
                var r = i[e];
                r.enumerable = r.enumerable || !1,
                r.configurable = !0,
                "value" in r && (r.writable = !0),
                Object.defineProperty(t, r.key, r)
            }
        }
        return function(i, e, r) {
            return e && t(i.prototype, e),
            r && t(i, r),
            i
        }
    } (),
    s = e(0),
    o = s.BigInteger,
    h = e(2),
    u = e(1),
    a = function() {
        function t() {
            r(this, t),
            this.ct = 1,
            this.p2 = null,
            this.sm3keybase = null,
            this.sm3c3 = null,
            this.key = new Array(32),
            this.keyOff = 0
        }
        return n(t, [{
            key: "reset",
            value: function() {
                this.sm3keybase = new h,
                this.sm3c3 = new h;
                var t = u.hexToArray(this.p2.getX().toBigInteger().toRadix(16)),
                i = u.hexToArray(this.p2.getY().toBigInteger().toRadix(16));
                this.sm3keybase.blockUpdate(t, 0, t.length),
                this.sm3c3.blockUpdate(t, 0, t.length),
                this.sm3keybase.blockUpdate(i, 0, i.length),
                this.ct = 1,
                this.nextKey()
            }
        },
        {
            key: "nextKey",
            value: function() {
                var t = new h(this.sm3keybase);
                t.update(this.ct >> 24 & 255),
                t.update(this.ct >> 16 & 255),
                t.update(this.ct >> 8 & 255),
                t.update(255 & this.ct),
                t.doFinal(this.key, 0),
                this.keyOff = 0,
                this.ct++
            }
        },
        {
            key: "initEncipher",
            value: function(t) {
                var i = u.generateKeyPairHex(),
                e = new o(i.privateKey, 16),
                r = i.publicKey;
                return this.p2 = t.multiply(e),
                this.reset(),
                r.length > 128 && (r = r.substr(r.length - 128)),
                r
            }
        },
        {
            key: "encryptBlock",
            value: function(t) {
                this.sm3c3.blockUpdate(t, 0, t.length);
                for (var i = 0; i < t.length; i++) this.keyOff === this.key.length && this.nextKey(),
                t[i] ^= 255 & this.key[this.keyOff++]
            }
        },
        {
            key: "initDecipher",
            value: function(t, i) {
                this.p2 = i.multiply(t),
                this.reset()
            }
        },
        {
            key: "decryptBlock",
            value: function(t) {
                for (var i = 0; i < t.length; i++) this.keyOff === this.key.length && this.nextKey(),
                t[i] ^= 255 & this.key[this.keyOff++];
                this.sm3c3.blockUpdate(t, 0, t.length)
            }
        },
        {
            key: "doFinal",
            value: function(t) {
                var i = u.hexToArray(this.p2.getY().toBigInteger().toRadix(16));
                this.sm3c3.blockUpdate(i, 0, i.length),
                this.sm3c3.doFinal(t, 0),
                this.reset()
            }
        },
        {
            key: "createPoint",
            value: function(t, i) {
                var e = "04" + t + i;
                return u.getGlobalCurve().decodePointHex(e)
            }
        }]),
        t
    } ();
    t.exports = a
}]);