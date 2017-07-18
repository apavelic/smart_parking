﻿using SmartParkingWeb.Core.Models;
using SmartParkingWeb.Core.Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace SmartParkingWeb.Web.Controllers
{
    public class HomeController : Controller
    {
        private ParkingService service;

        public HomeController()
        {
            service = new ParkingService();
        }

        public ActionResult Index()
        {
            return View();
        }

        public ActionResult Parking()
        {
            IEnumerable<ParkingViewModel> model = service.GetParking();
            return View(model);
        }

        public ActionResult Report()
        {
            return View();
        }

        public JsonResult GetParking()
        {
            return Json(service.GetParking(), JsonRequestBehavior.AllowGet);
        }

        public JsonResult GetStatistics(string from = null, string to = null)
        {
            var chartData = service.PrepareChartData(from, to);
            return Json(chartData, JsonRequestBehavior.AllowGet);
        }
    }
}